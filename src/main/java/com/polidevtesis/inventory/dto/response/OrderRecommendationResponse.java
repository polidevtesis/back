package com.polidevtesis.inventory.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRecommendationResponse {

    private LocalDateTime generatedAt;
    private BigDecimal budget;
    private List<RecommendationItem> recommendations;
    private BigDecimal totalEstimatedCost;
    private BigDecimal remainingBudget;
    private String modelUsed;
    private String rawAnalysis;

    @Data
    public static class RecommendationItem {
        private Long productId;
        private String productName;
        private String productSku;
        private Integer currentStock;
        private Integer minStock;
        private Double avgMonthlySales;
        private Integer suggestedOrderQty;
        private BigDecimal estimatedCost;
        private String justification;
    }
}
