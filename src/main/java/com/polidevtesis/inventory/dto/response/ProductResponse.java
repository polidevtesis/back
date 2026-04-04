package com.polidevtesis.inventory.dto.response;

import com.polidevtesis.inventory.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProductResponse {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal costPrice;
    private Integer stock;
    private Integer minStock;
    private String unit;
    private boolean lowStock;
    private Set<CategoryResponse> categories;
    private Set<ProviderResponse> providers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse from(Product p) {
        ProductResponse r = new ProductResponse();
        r.id = p.getId();
        r.sku = p.getSku();
        r.name = p.getName();
        r.description = p.getDescription();
        r.unitPrice = p.getUnitPrice();
        r.costPrice = p.getCostPrice();
        r.stock = p.getStock();
        r.minStock = p.getMinStock();
        r.unit = p.getUnit();
        r.lowStock = p.getStock() <= p.getMinStock();
        r.categories = p.getCategories().stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toSet());
        r.providers = p.getProviders().stream()
                .map(ProviderResponse::from)
                .collect(Collectors.toSet());
        r.createdAt = p.getCreatedAt();
        r.updatedAt = p.getUpdatedAt();
        return r;
    }
}
