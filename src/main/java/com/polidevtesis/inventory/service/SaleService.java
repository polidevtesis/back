package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.SaleRequest;
import com.polidevtesis.inventory.dto.response.SaleResponse;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.entity.Sale;
import com.polidevtesis.inventory.entity.SaleItem;
import com.polidevtesis.inventory.exception.InsufficientStockException;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.ProductRepository;
import com.polidevtesis.inventory.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final MovementService movementService;

    public Page<SaleResponse> findAll(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return saleRepository.findAllActive(from, to, pageable).map(SaleResponse::from);
    }

    public SaleResponse findById(Long id) {
        return SaleResponse.from(getActiveOrThrow(id));
    }

    @Transactional
    public SaleResponse create(SaleRequest request) {
        Sale sale = Sale.builder()
                .saleDate(request.getSaleDate())
                .notes(request.getNotes())
                .build();

        List<SaleItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var itemReq : request.getItems()) {
            Product product = productRepository.findByIdAndDeletedAtIsNull(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + itemReq.getProductId(), "PRODUCT_NOT_FOUND"));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new InsufficientStockException(product.getName(), product.getStock(), itemReq.getQuantity());
            }

            BigDecimal subtotal = product.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            SaleItem item = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(product.getUnitPrice())
                    .subtotal(subtotal)
                    .build();

            items.add(item);
            total = total.add(subtotal);

            // Decrement stock atomically within this transaction
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);
        }

        sale.setItems(items);
        sale.setTotalAmount(total);
        Sale saved = saleRepository.save(sale);

        // Create OUTPUT inventory movements for each item
        for (SaleItem item : saved.getItems()) {
            movementService.createOutputMovement(
                item.getProduct(),
                item.getQuantity(),
                "Sale #" + saved.getId(),
                "SALE-" + saved.getId()
            );
        }

        return SaleResponse.from(saved);
    }

    @Transactional
    public void cancel(Long id) {
        Sale sale = getActiveOrThrow(id);
        sale.setDeletedAt(LocalDateTime.now());
        saleRepository.save(sale);
        // Per spec: canceled sales do NOT reverse inventory
    }

    private Sale getActiveOrThrow(Long id) {
        return saleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Sale not found with id: " + id, "SALE_NOT_FOUND"));
    }
}
