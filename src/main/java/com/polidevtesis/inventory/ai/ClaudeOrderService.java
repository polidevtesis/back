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

    @Value("${ai.openai.model}")
    private String model;

    @Value("${ai.openai.api-url}")
    private String apiUrl;

    @Value("${ai.openai.max-tokens}")
    private int maxTokens;

    @Value("${ai.openai.analysis-days-default}")
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

        // Sort by criticality: biggest stock deficit first, then highest sales velocity.
        // Apply technical cap only after sorting so the most critical items are always included.
        products = products.stream()
                .sorted((a, b) -> {
                    int deficitCmp = Integer.compare(
                            b.getMinStock() - b.getStock(),
                            a.getMinStock() - a.getStock());
                    if (deficitCmp != 0) return deficitCmp;
                    return Long.compare(
                            salesMap.getOrDefault(b.getId(), 0L),
                            salesMap.getOrDefault(a.getId(), 0L));
                })
                .limit(MAX_PRODUCTS_IN_PROMPT)
                .collect(Collectors.toList());

        log.info("Products in deficit: {} (cap: {})", products.size(), MAX_PRODUCTS_IN_PROMPT);

        String stockTable = buildStockTable(products);
        String salesTable = buildSalesTable(products, salesMap, days);

        String prompt = buildPrompt(request.getBudget(), days, stockTable, salesTable, products.size());

        log.info("Calling Claude API for order recommendation. Model: {}, Budget: {}, Days: {}",
                model, request.getBudget(), days);

        // API key must NOT appear in logs
        String rawResponse = callClaudeApi(claudeApiKey, prompt);

        return parseResponse(rawResponse, request.getBudget(), products);
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    // Hard cap to keep prompts under ~50 KB; real inventories rarely exceed this in deficit
    private static final int MAX_PRODUCTS_IN_PROMPT = 200;

    private List<Product> getRelevantProducts(Set<Long> focusCategoryIds) {
        List<Product> all = productRepository.findAllActive();
        List<Product> filtered = (focusCategoryIds == null || focusCategoryIds.isEmpty())
                ? all
                : all.stream()
                        .filter(p -> p.getCategories().stream()
                                .anyMatch(c -> focusCategoryIds.contains(c.getId())))
                        .collect(Collectors.toList());

        return filtered.stream()
                .filter(p -> p.getStock() < p.getMinStock())
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

    private String buildPrompt(BigDecimal budget, int days, String stockTable, String salesTable,
                               int productCount) {
        return String.format("""
            You are an inventory optimization assistant for a bike and motorbike parts distributor.

            All %d products listed below are ALREADY below their minimum stock threshold (stock < minStock).
            They are pre-sorted by criticality: highest stock deficit first, then highest sales velocity.

            Your goal: select as many products as possible to reorder without exceeding the total budget of $%.2f.
            Use costPrice for all cost calculations.

            Analysis period: last %d days.

            === CURRENT STOCK (all items: stock < minStock, sorted by criticality) ===
            %s

            === SALES IN LAST %d DAYS ===
            %s

            Rules:
            - Include as many products as the budget allows, starting from the top of the table (most critical).
            - For each included product, order at least (minStock - currentStock) units to reach the minimum.
            - If budget is tight, reduce order quantities on lower-priority products before dropping them entirely.
            - Never exceed the total budget. Accumulate costs as you go and stop when budget is exhausted.
            - Do not recommend products with 0 sales AND 0 stock deficit (already filtered out).
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
            productCount, budget, days, stockTable, days, salesTable
        );
    }

    private String callClaudeApi(String apiKey, String prompt) {
        try {
            String body = objectMapper.writeValueAsString(Map.of(
                "model", model,
                "max_output_tokens", maxTokens,
                "input", List.of(Map.of("role", "user", "content", prompt))
            ));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("OpenAI API error. Status: {}", response.statusCode());
                throw new RuntimeException("OpenAI API returned status " + response.statusCode()
                    + ". Check your API key and account limits.");
            }

            return response.body();

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API: " + e.getMessage(), e);
        }
    }

    private OrderRecommendationResponse parseResponse(
            String rawResponse, BigDecimal budget, List<Product> products) {

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        try {
            JsonNode root = objectMapper.readTree(rawResponse);

            JsonNode outputArr = root.path("output");
            if (!outputArr.isArray() || outputArr.isEmpty()) {
                log.error("Unexpected OpenAI response structure. Raw: {}", rawResponse);
                throw new RuntimeException("OpenAI response missing 'output' array");
            }
            // OpenAI Responses API: output[0].content[0].text contains the JSON string
            String contentText = outputArr.get(0).path("content").get(0).path("text").asText();

            // Strip markdown code fences the model sometimes adds despite the instruction
            contentText = contentText.replaceAll("(?s)^```(?:json)?\\s*", "").replaceAll("(?s)\\s*```$", "").trim();

            log.debug("OpenAI content text: {}", contentText);

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

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse OpenAI response. Raw: {}", rawResponse, e);
            throw new RuntimeException("Failed to parse OpenAI API response: " + e.getMessage());
        }
    }
}
