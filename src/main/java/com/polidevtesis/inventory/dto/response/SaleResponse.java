package com.polidevtesis.inventory.dto.response;

import com.polidevtesis.inventory.entity.Sale;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SaleResponse {

    private Long id;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private String notes;
    private List<SaleItemResponse> items;
    private LocalDateTime createdAt;

    public static SaleResponse from(Sale s) {
        SaleResponse r = new SaleResponse();
        r.id = s.getId();
        r.saleDate = s.getSaleDate();
        r.totalAmount = s.getTotalAmount();
        r.notes = s.getNotes();
        r.items = s.getItems().stream()
                .map(SaleItemResponse::from)
                .collect(Collectors.toList());
        r.createdAt = s.getCreatedAt();
        return r;
    }
}
