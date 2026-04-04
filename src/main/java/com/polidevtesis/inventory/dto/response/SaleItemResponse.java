package com.polidevtesis.inventory.dto.response;

import com.polidevtesis.inventory.entity.SaleItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productSku;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public static SaleItemResponse from(SaleItem si) {
        SaleItemResponse r = new SaleItemResponse();
        r.id = si.getId();
        r.productId = si.getProduct().getId();
        r.productName = si.getProduct().getName();
        r.productSku = si.getProduct().getSku();
        r.quantity = si.getQuantity();
        r.unitPrice = si.getUnitPrice();
        r.subtotal = si.getSubtotal();
        return r;
    }
}
