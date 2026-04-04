package com.polidevtesis.inventory.repository;

import com.polidevtesis.inventory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndDeletedAtIsNull(Long id);

    boolean existsBySkuAndDeletedAtIsNull(String sku);

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN p.categories c
        WHERE p.deletedAt IS NULL
          AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:sku IS NULL OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :sku, '%')))
          AND (:categoryId IS NULL OR c.id = :categoryId)
        """)
    Page<Product> search(
        @Param("name") String name,
        @Param("sku") String sku,
        @Param("categoryId") Long categoryId,
        Pageable pageable
    );

    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL AND p.stock <= p.minStock ORDER BY p.stock ASC")
    List<Product> findLowStock();

    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
    List<Product> findAllActive();
}
