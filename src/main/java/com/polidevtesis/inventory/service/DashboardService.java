package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.response.DashboardSummaryResponse;
import com.polidevtesis.inventory.dto.response.MovementResponse;
import com.polidevtesis.inventory.dto.response.ProductResponse;
import com.polidevtesis.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final InventoryMovementRepository movementRepository;

    public DashboardSummaryResponse getSummary(LocalDateTime from, LocalDateTime to) {
        long totalProducts = productRepository.findAllActive().size();
        long totalCategories = categoryRepository.findAllByDeletedAtIsNull().size();
        long totalProviders = providerRepository.findAllByDeletedAtIsNull(PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
        long totalSales = saleRepository.countActive(from, to);
        long lowStockCount = productRepository.findLowStock().size();

        return DashboardSummaryResponse.builder()
                .totalProducts(totalProducts)
                .totalCategories(totalCategories)
                .totalProviders(totalProviders)
                .totalSalesPeriod(totalSales)
                .lowStockCount(lowStockCount)
                .build();
    }

    public List<ProductResponse> getLowStock() {
        return productRepository.findLowStock().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getSalesByCategory(LocalDateTime from, LocalDateTime to) {
        return saleItemRepository.findSalesByCategory(from, to).stream()
                .map(row -> {
                    Map<String, Object> entry = new LinkedHashMap<>();
                    entry.put("categoryId", row[0]);
                    entry.put("categoryName", row[1]);
                    entry.put("totalRevenue", row[2]);
                    return entry;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getTopProducts(LocalDateTime from, LocalDateTime to, int limit) {
        return saleItemRepository.findTopProducts(from, to, PageRequest.of(0, limit)).stream()
                .map(row -> {
                    Map<String, Object> entry = new LinkedHashMap<>();
                    entry.put("productId", row[0]);
                    entry.put("productName", row[1]);
                    entry.put("totalUnitsSold", row[2]);
                    return entry;
                })
                .collect(Collectors.toList());
    }

    public List<MovementResponse> getStockHistory(Long productId) {
        return movementRepository.findByProductIdOrderByMovedAtAsc(productId).stream()
                .map(MovementResponse::from)
                .collect(Collectors.toList());
    }
}
