package com.polidevtesis.inventory.repository;

import com.polidevtesis.inventory.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Page<Provider> findAllByDeletedAtIsNull(Pageable pageable);

    Optional<Provider> findByIdAndDeletedAtIsNull(Long id);
}
