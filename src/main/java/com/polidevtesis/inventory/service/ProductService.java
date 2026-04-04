package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.ProductRequest;
import com.polidevtesis.inventory.dto.response.ProductResponse;
import com.polidevtesis.inventory.entity.Category;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.entity.Provider;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.CategoryRepository;
import com.polidevtesis.inventory.repository.ProductRepository;
import com.polidevtesis.inventory.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;

    public Page<ProductResponse> search(String name, String sku, Long categoryId, Pageable pageable) {
        return productRepository.search(name, sku, categoryId, pageable)
                .map(ProductResponse::from);
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getActiveOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySkuAndDeletedAtIsNull(request.getSku())) {
            throw new IllegalArgumentException("A product with SKU '" + request.getSku() + "' already exists");
        }

        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice())
                .costPrice(request.getCostPrice())
                .stock(request.getStock() != null ? request.getStock() : 0)
                .minStock(request.getMinStock() != null ? request.getMinStock() : 5)
                .unit(request.getUnit())
                .categories(resolveCategories(request.getCategoryIds()))
                .providers(resolveProviders(request.getProviderIds()))
                .build();

        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getActiveOrThrow(id);

        if (!product.getSku().equals(request.getSku())
                && productRepository.existsBySkuAndDeletedAtIsNull(request.getSku())) {
            throw new IllegalArgumentException("A product with SKU '" + request.getSku() + "' already exists");
        }

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setCostPrice(request.getCostPrice());
        product.setMinStock(request.getMinStock() != null ? request.getMinStock() : 5);
        product.setUnit(request.getUnit());
        product.setCategories(resolveCategories(request.getCategoryIds()));
        product.setProviders(resolveProviders(request.getProviderIds()));

        return ProductResponse.from(productRepository.save(product));
    }

    public void delete(Long id) {
        Product product = getActiveOrThrow(id);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public List<ProductResponse> findLowStock() {
        return productRepository.findLowStock().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public Product getActiveOrThrow(Long id) {
        return productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Product not found with id: " + id, "PRODUCT_NOT_FOUND"));
    }

    private Set<Category> resolveCategories(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        return ids.stream()
                .map(cid -> categoryRepository.findByIdAndDeletedAtIsNull(cid)
                        .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found with id: " + cid, "CATEGORY_NOT_FOUND")))
                .collect(Collectors.toSet());
    }

    private Set<Provider> resolveProviders(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        return ids.stream()
                .map(pid -> providerRepository.findByIdAndDeletedAtIsNull(pid)
                        .orElseThrow(() -> new ResourceNotFoundException(
                            "Provider not found with id: " + pid, "PROVIDER_NOT_FOUND")))
                .collect(Collectors.toSet());
    }
}
