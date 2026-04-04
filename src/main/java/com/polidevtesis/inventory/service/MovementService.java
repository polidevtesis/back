package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.MovementRequest;
import com.polidevtesis.inventory.dto.response.MovementResponse;
import com.polidevtesis.inventory.entity.InventoryMovement;
import com.polidevtesis.inventory.entity.MovementType;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.InventoryMovementRepository;
import com.polidevtesis.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final InventoryMovementRepository movementRepository;
    private final ProductRepository productRepository;

    public Page<MovementResponse> search(
            Long productId, MovementType type,
            LocalDateTime from, LocalDateTime to,
            Pageable pageable) {
        return movementRepository.search(productId, type, from, to, pageable)
                .map(MovementResponse::from);
    }

    public MovementResponse findById(Long id) {
        return MovementResponse.from(movementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Movement not found with id: " + id, "MOVEMENT_NOT_FOUND")));
    }

    public List<MovementResponse> findByProduct(Long productId) {
        return movementRepository.findByProductIdOrderByMovedAtAsc(productId).stream()
                .map(MovementResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovementResponse register(MovementRequest request) {
        if (request.getType() == MovementType.OUTPUT) {
            throw new IllegalArgumentException(
                "OUTPUT movements are created automatically by sales. Use INPUT or ADJUSTMENT.");
        }

        Product product = productRepository.findByIdAndDeletedAtIsNull(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Product not found with id: " + request.getProductId(), "PRODUCT_NOT_FOUND"));

        int delta = request.getType() == MovementType.INPUT
                ? request.getQuantity()
                : request.getQuantity(); // ADJUSTMENT can be positive or negative if needed — keep positive, caller decides type

        product.setStock(product.getStock() + delta);
        productRepository.save(product);

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .type(request.getType())
                .quantity(request.getQuantity())
                .reason(request.getReason())
                .reference(request.getReference())
                .movedAt(request.getMovedAt() != null ? request.getMovedAt() : LocalDateTime.now())
                .build();

        return MovementResponse.from(movementRepository.save(movement));
    }

    /**
     * Internal method used by SaleService to create OUTPUT movements when a sale is registered.
     * Not exposed directly through REST.
     */
    @Transactional
    public InventoryMovement createOutputMovement(Product product, int quantity, String reason, String reference) {
        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .type(MovementType.OUTPUT)
                .quantity(quantity)
                .reason(reason)
                .reference(reference)
                .movedAt(LocalDateTime.now())
                .build();
        return movementRepository.save(movement);
    }
}
