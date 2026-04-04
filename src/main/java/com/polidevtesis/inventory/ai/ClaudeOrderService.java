package com.polidevtesis.inventory.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polidevtesis.inventory.dto.request.OrderRecommendationRequest;
import com.polidevtesis.inventory.dto.response.OrderRecommendationResponse;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.repository.ProductRepository;
import com.polidevtesis.inventory.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaudeOrderService {

    private final ProductRepository productRepository;
    private final SaleItemRepository saleItemRepository;
    private final ObjectMapper objectMapper;

    @Value("${ai.claude.model}")
    private String model;

    @Value("${ai.claude.api-url}")
    private String apiUrl;

    @Value("${ai.claude.max-tokens}")
    private int maxTokens;

    @Value("${ai.claude.analysis-days-default}")
    private int analysisDaysDefault;

    /**
     * Calls the Claude API using the provided API key (never stored).
     * Builds a prompt with current stock + sales history + budget and returns structured recommendations.
     *
     * @param claudeApiKey  The user-supplied Anthropic API key. Used only for this request.
     * @param request       Parameters: budget, analysisDays, optional focusCategoryIds.
     */
    public OrderRecommendationResponse recommend(String claudeApiKey, OrderRecommendationRequest request) {
        int days = request.getAnalysisDays() != null ? request.getAnalysisDays() : analysisDaysDefault;
        LocalDateTime since = LocalDateTime.now().minusDays(days);

        // --- Build context data ---
        List<Product> products = getRelevantProducts(request.getFocusCategoryIds());
        Map<Long, Long> salesMap = buildSalesMap(since);

        String stockTable = buildStockTable(products);
        String salesTable = buildSalesTable(products, salesMap, days);

        String prompt = buildPrompt(request.getBudget(), days, stockTable, salesTable);

        log.info("Calling Claude API for order recommendation. Model: {}, Budget: {}, Days: {}",
                model, request.getBudget(), days);

        // API key must NOT appear in logs
        String rawResponse = callClaudeApi(claudeApiKey, prompt);

        return parseResponse(rawResponse, request.getBudget(), products);
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private List<Product> getRelevantProducts(Set<Long> focusCategoryIds) {
        List<Product> all = productRepository.findAllActive();
        if (focusCategoryIds == null || focusCategoryIds.isEmpty()) {
            return all;
        }
        return all.stream()
                .filter(p -> p.getCategories().stream()
                        .anyMatch(c -> focusCategoryIds.contains(c.getId())))
                .collect(Collectors.toList());
    }

    private Map<Long, Long> buildSalesMap(LocalDateTime since) {
        return saleItemRepository.findSalesSince(since).stream()
                .collect(Collectors.toMap(
                    row -> (Long) row[0],
                    row -> (Long) row[1]
                ));
    }

    private String buildStockTable(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("productId | sku | name | currentStock | minStock | costPrice\n");
        sb.append("-".repeat(80)).append("\n");
        for (Product p : products) {
            sb.append(String.format("%d | %s | %s | %d | %d | %.2f%n",
                    p.getId(), p.getSku(), p.getName(),
                    p.getStock(), p.getMinStock(), p.getCostPrice()));
        }
        return sb.toString();
    }

    private String buildSalesTable(List<Product> products, Map<Long, Long> salesMap, int days) {
        StringBuilder sb = new StringBuilder();
        sb.append("productId | name | totalSoldInPeriod | avgMonthlySales\n");
        sb.append("-".repeat(80)).append("\n");
        double months = days / 30.0;
        for (Product p : products) {
            long total = salesMap.getOrDefault(p.getId(), 0L);
            double avgMonthly = months > 0 ? total / months : 0;
            sb.append(String.format("%d | %s | %d | %.1f%n",
                    p.getId(), p.getName(), total, avgMonthly));
        }
        return sb.toString();
    }

    private String buildPrompt(BigDecimal budget, int days, String stockTable, String salesTable) {
        return String.format("""
            You are an inventory optimization assistant for a bike and motorbike parts distributor.

            Given the following data, recommend which products to reorder and in what quantities
            to maximize sales potential while staying within a total budget of $%.2f.

            Analysis period: last %d days.

            === CURRENT STOCK ===
            %s

            === SALES IN LAST %d DAYS ===
            %s

            Rules:
            - Prioritize products at or below their minStock threshold.
            - Prefer high-velocity items (high avgMonthlySales relative to current stock).
            - Do not exceed the total budget (use costPrice for calculations).
            - Only recommend products worth restocking (skip items with 0 sales and adequate stock).
            - Return ONLY valid JSON — no markdown, no explanation outside the JSON.

            Required JSON schema:
            {
              "recommendations": [
                {
                  "productId": <number>,
                  "suggestedOrderQty": <number>,
                  "estimatedCost": <number>,
                  "justification": "<string>"
                }
              ],
              "totalEstimatedCost": <number>
            }
            """,
            budget, days, stockTable, days, salesTable
        );
    }

    private String callClaudeApi(String apiKey, String prompt) {
        try {
            String body = objectMapper.writeValueAsString(Map.of(
                "model", model,
                "max_tokens", maxTokens,
                "messages", List.of(Map.of("role", "user", "content", prompt))
            ));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", apiKey)
                    .header("anthropic-version", "2023-06-01")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Claude API error. Status: {}", response.statusCode());
                throw new RuntimeException("Claude API returned status " + response.statusCode()
                    + ". Check your API key and account limits.");
            }

            return response.body();

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Claude API: " + e.getMessage(), e);
        }
    }

    private OrderRecommendationResponse parseResponse(
            String rawResponse, BigDecimal budget, List<Product> products) {

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            // Claude API response: root.content[0].text contains the actual JSON string
            String contentText = root.path("content").get(0).path("text").asText();

            JsonNode rec = objectMapper.readTree(contentText);

            List<OrderRecommendationResponse.RecommendationItem> items = new ArrayList<>();
            JsonNode recsNode = rec.path("recommendations");

            for (JsonNode node : recsNode) {
                long productId = node.path("productId").asLong();
                Product p = productMap.get(productId);
                if (p == null) continue;

                OrderRecommendationResponse.RecommendationItem item =
                        new OrderRecommendationResponse.RecommendationItem();
                item.setProductId(productId);
                item.setProductName(p.getName());
                item.setProductSku(p.getSku());
                item.setCurrentStock(p.getStock());
                item.setMinStock(p.getMinStock());
                item.setSuggestedOrderQty(node.path("suggestedOrderQty").asInt());
                item.setEstimatedCost(BigDecimal.valueOf(node.path("estimatedCost").asDouble()));
                item.setJustification(node.path("justification").asText());
                items.add(item);
            }

            BigDecimal totalCost = BigDecimal.valueOf(rec.path("totalEstimatedCost").asDouble());

            OrderRecommendationResponse response = new OrderRecommendationResponse();
            response.setGeneratedAt(LocalDateTime.now());
            response.setBudget(budget);
            response.setRecommendations(items);
            response.setTotalEstimatedCost(totalCost);
            response.setRemainingBudget(budget.subtract(totalCost));
            response.setModelUsed(model);
            response.setRawAnalysis(contentText);

            return response;

        } catch (Exception e) {
            log.error("Failed to parse Claude response", e);
            throw new RuntimeException("Failed to parse Claude API response. Raw response logged at DEBUG level.");
        }
    }
}
