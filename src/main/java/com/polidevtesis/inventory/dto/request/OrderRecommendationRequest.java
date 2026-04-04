package com.polidevtesis.inventory.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderRecommendationRequest {

    @NotNull(message = "Budget is required")
    @DecimalMin(value = "1.0", message = "Budget must be greater than 0")
    private BigDecimal budget;

    @Min(1)
    private Integer analysisDays = 90;

    // Optional: restrict analysis to specific categories
    private Set<Long> focusCategoryIds;
}
