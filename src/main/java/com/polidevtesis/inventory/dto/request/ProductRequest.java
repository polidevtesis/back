package com.polidevtesis.inventory.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductRequest {

    @NotBlank(message = "SKU is required")
    @Size(max = 50)
    private String sku;

    @NotBlank(message = "Name is required")
    @Size(max = 200)
    private String name;

    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal unitPrice;

    @NotNull(message = "Cost price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPrice;

    @Min(0)
    private Integer stock = 0;

    @Min(0)
    private Integer minStock = 5;

    @Size(max = 30)
    private String unit;

    private Set<Long> categoryIds;

    private Set<Long> providerIds;
}
