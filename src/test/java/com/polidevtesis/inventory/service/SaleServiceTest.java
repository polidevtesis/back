package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.SaleItemRequest;
import com.polidevtesis.inventory.dto.request.SaleRequest;
import com.polidevtesis.inventory.dto.response.SaleResponse;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.entity.Sale;
import com.polidevtesis.inventory.entity.SaleItem;
import com.polidevtesis.inventory.exception.InsufficientStockException;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.ProductRepository;
import com.polidevtesis.inventory.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SaleService - Pruebas Unitarias")
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MovementService movementService;

    @InjectMocks
    private SaleService saleService;

    private Product productoCadena;
    private Product productoLlanta;

    @BeforeEach
    void setUp() {
        productoCadena = Product.builder()
                .id(1L)
                .sku("MC001")
                .name("Cadena moto 420")
                .unitPrice(new BigDecimal("35000"))
                .costPrice(new BigDecimal("22000"))
                .stock(10)
                .minStock(3)
                .build();

        productoLlanta = Product.builder()
                .id(2L)
                .sku("MC002")
                .name("Llanta delantera 2.75-17")
                .unitPrice(new BigDecimal("120000"))
                .costPrice(new BigDecimal("85000"))
                .stock(5)
                .minStock(2)
                .build();
    }

    // ─── RF-13 Registrar venta ───────────────────────────────────────────────────

    @Test
    @DisplayName("RF-13 | Debe registrar una venta con un producto correctamente")
    void crear_ventaUnProducto_exitoso() {
        SaleItemRequest itemReq = new SaleItemRequest();
        itemReq.setProductId(1L);
        itemReq.setQuantity(2);

        SaleRequest request = new SaleRequest();
        request.setSaleDate(LocalDateTime.now());
        request.setItems(List.of(itemReq));

        List<SaleItem> items = new ArrayList<>();
        SaleItem saleItem = SaleItem.builder()
                .product(productoCadena)
                .quantity(2)
                .unitPrice(new BigDecimal("35000"))
                .subtotal(new BigDecimal("70000"))
                .build();
        items.add(saleItem);

        Sale ventaGuardada = Sale.builder()
                .id(1L)
                .saleDate(request.getSaleDate())
                .totalAmount(new BigDecimal("70000"))
                .items(items)
                .build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCadena));
        when(productRepository.save(any(Product.class))).thenReturn(productoCadena);
        when(saleRepository.save(any(Sale.class))).thenReturn(ventaGuardada);

        SaleResponse result = saleService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTotalAmount()).isEqualByComparingTo("70000");
    }

    @Test
    @DisplayName("RF-13 | Debe registrar venta con múltiples productos y calcular total correcto")
    void crear_ventaMultiplesProductos_totalCorrecto() {
        SaleItemRequest itemCadena = new SaleItemRequest();
        itemCadena.setProductId(1L);
        itemCadena.setQuantity(1);

        SaleItemRequest itemLlanta = new SaleItemRequest();
        itemLlanta.setProductId(2L);
        itemLlanta.setQuantity(2);

        SaleRequest request = new SaleRequest();
        request.setSaleDate(LocalDateTime.now());
        request.setItems(List.of(itemCadena, itemLlanta));

        // Total esperado: 35.000 + (2 x 120.000) = 275.000
        List<SaleItem> items = new ArrayList<>();
        items.add(SaleItem.builder().product(productoCadena).quantity(1)
                .unitPrice(new BigDecimal("35000")).subtotal(new BigDecimal("35000")).build());
        items.add(SaleItem.builder().product(productoLlanta).quantity(2)
                .unitPrice(new BigDecimal("120000")).subtotal(new BigDecimal("240000")).build());

        Sale ventaGuardada = Sale.builder()
                .id(2L).saleDate(request.getSaleDate())
                .totalAmount(new BigDecimal("275000")).items(items).build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCadena));
        when(productRepository.findByIdAndDeletedAtIsNull(2L))
                .thenReturn(Optional.of(productoLlanta));
        when(productRepository.save(any(Product.class))).thenReturn(productoCadena);
        when(saleRepository.save(any(Sale.class))).thenReturn(ventaGuardada);

        SaleResponse result = saleService.create(request);

        assertThat(result.getTotalAmount()).isEqualByComparingTo("275000");
        assertThat(result.getItems()).hasSize(2);
    }

    // ─── RF-14 Actualizar stock después de venta ─────────────────────────────────

    @Test
    @DisplayName("RF-14 | Debe descontar el stock del producto al registrar la venta")
    void crear_venta_descontaStockProducto() {
        int stockInicial = productoCadena.getStock();
        int cantidadVenta = 3;

        SaleItemRequest itemReq = new SaleItemRequest();
        itemReq.setProductId(1L);
        itemReq.setQuantity(cantidadVenta);

        SaleRequest request = new SaleRequest();
        request.setSaleDate(LocalDateTime.now());
        request.setItems(List.of(itemReq));

        List<SaleItem> items = List.of(
                SaleItem.builder().product(productoCadena).quantity(cantidadVenta)
                        .unitPrice(new BigDecimal("35000"))
                        .subtotal(new BigDecimal("105000")).build());

        Sale ventaGuardada = Sale.builder().id(3L).saleDate(request.getSaleDate())
                .totalAmount(new BigDecimal("105000")).items(items).build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCadena));
        when(productRepository.save(any(Product.class))).thenReturn(productoCadena);
        when(saleRepository.save(any(Sale.class))).thenReturn(ventaGuardada);

        saleService.create(request);

        // Verificar que el stock fue descontado en el objeto producto
        assertThat(productoCadena.getStock()).isEqualTo(stockInicial - cantidadVenta);
        verify(productRepository, atLeastOnce()).save(productoCadena);
    }

    // ─── RF-14 Stock insuficiente ────────────────────────────────────────────────

    @Test
    @DisplayName("RF-14 | Debe lanzar InsufficientStockException cuando el stock es insuficiente")
    void crear_ventaStockInsuficiente_lanzaExcepcion() {
        SaleItemRequest itemReq = new SaleItemRequest();
        itemReq.setProductId(1L);
        itemReq.setQuantity(50); // solicitar más del stock disponible (10)

        SaleRequest request = new SaleRequest();
        request.setSaleDate(LocalDateTime.now());
        request.setItems(List.of(itemReq));

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCadena));

        assertThatThrownBy(() -> saleService.create(request))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Cadena moto 420");

        verify(saleRepository, never()).save(any());
    }

    @Test
    @DisplayName("RF-14 | Debe lanzar excepción cuando el stock disponible es exactamente 0")
    void crear_ventaStockCero_lanzaExcepcion() {
        productoCadena.setStock(0);

        SaleItemRequest itemReq = new SaleItemRequest();
        itemReq.setProductId(1L);
        itemReq.setQuantity(1);

        SaleRequest request = new SaleRequest();
        request.setSaleDate(LocalDateTime.now());
        request.setItems(List.of(itemReq));

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCadena));

        assertThatThrownBy(() -> saleService.create(request))
                .isInstanceOf(InsufficientStockException.class);
    }

    // ─── RF-15 Consultar historial de ventas ─────────────────────────────────────

    @Test
    @DisplayName("RF-15 | Debe retornar venta existente por ID")
    void buscarPorId_ventaExistente_retornaResponse() {
        Sale venta = Sale.builder().id(5L).saleDate(LocalDateTime.now())
                .totalAmount(new BigDecimal("35000")).items(new ArrayList<>()).build();

        when(saleRepository.findByIdAndDeletedAtIsNull(5L)).thenReturn(Optional.of(venta));

        SaleResponse result = saleService.findById(5L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    @DisplayName("RF-15 | Debe lanzar excepción al buscar venta con ID inexistente")
    void buscarPorId_ventaInexistente_lanzaExcepcion() {
        when(saleRepository.findByIdAndDeletedAtIsNull(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    // ─── Cancelar venta ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe marcar deletedAt al cancelar una venta")
    void cancelar_ventaExistente_marcaDeletedAt() {
        Sale venta = Sale.builder().id(5L).saleDate(LocalDateTime.now())
                .totalAmount(new BigDecimal("35000")).items(new ArrayList<>()).build();

        when(saleRepository.findByIdAndDeletedAtIsNull(5L)).thenReturn(Optional.of(venta));
        when(saleRepository.save(any(Sale.class))).thenReturn(venta);

        saleService.cancel(5L);

        verify(saleRepository).save(argThat(s -> s.getDeletedAt() != null));
    }

    // ─── Producto no encontrado en venta ─────────────────────────────────────────

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el producto de la venta no existe")
    void crear_conProductoInexistente_lanzaExcepcion() {
        SaleItemRequest itemReq = new SaleItemRequest();
        itemReq.setProductId(77L);
        itemReq.setQuantity(1);

        SaleRequest request = new SaleRequest();
        request.setSaleDate(LocalDateTime.now());
        request.setItems(List.of(itemReq));

        when(productRepository.findByIdAndDeletedAtIsNull(77L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.create(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
