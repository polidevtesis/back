package com.polidevtesis.inventory.repository;

import com.polidevtesis.inventory.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    // Returns [productId, productName, totalQty] for top-selling products in a period
    @Query("""
        SELECT si.product.id, si.product.name, SUM(si.quantity)
        FROM SaleItem si
        WHERE si.sale.deletedAt IS NULL
          AND si.sale.saleDate >= :from
          AND si.sale.saleDate <= :to
        GROUP BY si.product.id, si.product.name
        ORDER BY SUM(si.quantity) DESC
        """)
    List<Object[]> findTopProducts(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to,
        org.springframework.data.domain.Pageable pageable
    );

    // Returns [categoryId, categoryName, totalRevenue] for dashboard chart
    @Query("""
        SELECT c.id, c.name, SUM(si.subtotal)
        FROM SaleItem si
        JOIN si.product.categories c
        WHERE si.sale.deletedAt IS NULL
          AND (:from IS NULL OR si.sale.saleDate >= :from)
          AND (:to IS NULL OR si.sale.saleDate <= :to)
        GROUP BY c.id, c.name
        ORDER BY SUM(si.subtotal) DESC
        """)
    List<Object[]> findSalesByCategory(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    // Returns [productId, SUM(quantity)] for products sold in last N days (used by AI)
    @Query("""
        SELECT si.product.id, SUM(si.quantity)
        FROM SaleItem si
        WHERE si.sale.deletedAt IS NULL
          AND si.sale.saleDate >= :from
        GROUP BY si.product.id
        """)
    List<Object[]> findSalesSince(@Param("from") LocalDateTime from);
}
