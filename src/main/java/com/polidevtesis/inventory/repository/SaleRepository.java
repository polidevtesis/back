package com.polidevtesis.inventory.repository;

import com.polidevtesis.inventory.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findByIdAndDeletedAtIsNull(Long id);

    @Query("""
        SELECT s FROM Sale s
        WHERE s.deletedAt IS NULL
          AND (:from IS NULL OR s.saleDate >= :from)
          AND (:to IS NULL OR s.saleDate <= :to)
        """)
    Page<Sale> findAllActive(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to,
        Pageable pageable
    );

    @Query("""
        SELECT COUNT(s) FROM Sale s
        WHERE s.deletedAt IS NULL
          AND (:from IS NULL OR s.saleDate >= :from)
          AND (:to IS NULL OR s.saleDate <= :to)
        """)
    long countActive(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
}
