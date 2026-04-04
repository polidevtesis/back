package com.polidevtesis.inventory.dto.response;

import com.polidevtesis.inventory.entity.InventoryMovement;
import com.polidevtesis.inventory.entity.MovementType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productSku;
    private MovementType type;
    private Integer quantity;
    private String reason;
    private String reference;
    private LocalDateTime movedAt;
    private LocalDateTime createdAt;

    public static MovementResponse from(InventoryMovement m) {
        MovementResponse r = new MovementResponse();
        r.id = m.getId();
        r.productId = m.getProduct().getId();
        r.productName = m.getProduct().getName();
        r.productSku = m.getProduct().getSku();
        r.type = m.getType();
        r.quantity = m.getQuantity();
        r.reason = m.getReason();
        r.reference = m.getReference();
        r.movedAt = m.getMovedAt();
        r.createdAt = m.getCreatedAt();
        return r;
    }
}
