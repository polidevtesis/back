package com.polidevtesis.inventory.repository;

import com.polidevtesis.inventory.entity.InventoryMovement;
import com.polidevtesis.inventory.entity.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {

    @Query("""
        SELECT m FROM InventoryMovement m
        WHERE (:productId IS NULL OR m.product.id = :productId)
          AND (:type IS NULL OR m.type = :type)
          AND (:from IS NULL OR m.movedAt >= :from)
          AND (:to IS NULL OR m.movedAt <= :to)
        ORDER BY m.movedAt DESC
        """)
    Page<InventoryMovement> search(
        @Param("productId") Long productId,
        @Param("type") MovementType type,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to,
        Pageable pageable
    );

    // Stock history for a single product ordered chronologically
    List<InventoryMovement> findByProductIdOrderByMovedAtAsc(Long productId);
}
