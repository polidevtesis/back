package com.polidevtesis.inventory.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryResponse {
    private long totalProducts;
    private long totalCategories;
    private long totalProviders;
    private long totalSalesPeriod;
    private long lowStockCount;
}
