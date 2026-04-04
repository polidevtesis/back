package com.polidevtesis.inventory.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleRequest {

    @NotNull(message = "Sale date is required")
    private LocalDateTime saleDate;

    private String notes;

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<SaleItemRequest> items;
}
