package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.ProductRequest;
import com.polidevtesis.inventory.dto.response.ProductResponse;
import com.polidevtesis.inventory.entity.Category;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.CategoryRepository;
import com.polidevtesis.inventory.repository.ProductRepository;
import com.polidevtesis.inventory.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService - Pruebas Unitarias")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProductService productService;

    private Product productoBase;
    private ProductRequest requestBase;

    @BeforeEach
    void setUp() {
        productoBase = Product.builder()
                .id(1L)
                .sku("BC001")
                .name("Pastilla de freno bicicleta")
                .unitPrice(new BigDecimal("15000"))
                .costPrice(new BigDecimal("10000"))
                .stock(20)
                .minStock(5)
                .unit("und")
                .build();

        requestBase = new ProductRequest();
        requestBase.setSku("BC001");
        requestBase.setName("Pastilla de freno bicicleta");
        requestBase.setUnitPrice(new BigDecimal("15000"));
        requestBase.setCostPrice(new BigDecimal("10000"));
        requestBase.setStock(20);
        requestBase.setMinStock(5);
        requestBase.setUnit("und");
    }

    // ─── RF-01 Crear producto ────────────────────────────────────────────────────

    @Test
    @DisplayName("RF-01 | Debe crear un producto cuando el SKU no existe")
    void crear_productoNuevo_exitoso() {
        when(productRepository.existsBySkuAndDeletedAtIsNull("BC001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(productoBase);

        ProductResponse result = productService.create(requestBase);

        assertThat(result).isNotNull();
        assertThat(result.getSku()).isEqualTo("BC001");
        assertThat(result.getName()).isEqualTo("Pastilla de freno bicicleta");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("RF-01 | Debe lanzar excepción cuando el SKU ya existe")
    void crear_productoSkuDuplicado_lanzaExcepcion() {
        when(productRepository.existsBySkuAndDeletedAtIsNull("BC001")).thenReturn(true);

        assertThatThrownBy(() -> productService.create(requestBase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("BC001");

        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("RF-01 | Stock debe ser 0 cuando no se especifica en la solicitud")
    void crear_producto_stockPorDefectoCero() {
        requestBase.setStock(null);
        Product productoSinStock = Product.builder()
                .id(2L).sku("BC001").name("Pastilla de freno bicicleta")
                .unitPrice(new BigDecimal("15000")).costPrice(new BigDecimal("10000"))
                .stock(0).minStock(5).unit("und").build();

        when(productRepository.existsBySkuAndDeletedAtIsNull("BC001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(productoSinStock);

        ProductResponse result = productService.create(requestBase);

        assertThat(result.getStock()).isEqualTo(0);
    }

    // ─── RF-02 Consultar producto ────────────────────────────────────────────────

    @Test
    @DisplayName("RF-02 | Debe retornar el producto cuando el ID existe")
    void buscarPorId_productoExistente_retornaResponse() {
        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoBase));

        ProductResponse result = productService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSku()).isEqualTo("BC001");
    }

    @Test
    @DisplayName("RF-02 | Debe lanzar ResourceNotFoundException cuando el producto no existe")
    void buscarPorId_productoNoExistente_lanzaExcepcion() {
        when(productRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── RF-03 Editar producto ───────────────────────────────────────────────────

    @Test
    @DisplayName("RF-03 | Debe actualizar nombre y precio de un producto existente")
    void actualizar_productoExistente_exitoso() {
        requestBase.setName("Pastilla de freno premium");
        requestBase.setUnitPrice(new BigDecimal("18000"));

        Product productoActualizado = Product.builder()
                .id(1L).sku("BC001").name("Pastilla de freno premium")
                .unitPrice(new BigDecimal("18000")).costPrice(new BigDecimal("10000"))
                .stock(20).minStock(5).unit("und").build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoBase));
        when(productRepository.save(any(Product.class))).thenReturn(productoActualizado);

        ProductResponse result = productService.update(1L, requestBase);

        assertThat(result.getName()).isEqualTo("Pastilla de freno premium");
        assertThat(result.getUnitPrice()).isEqualByComparingTo("18000");
    }

    @Test
    @DisplayName("RF-03 | Debe lanzar excepción si el nuevo SKU ya pertenece a otro producto")
    void actualizar_skuDuplicadoOtroProducto_lanzaExcepcion() {
        requestBase.setSku("BC002");

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoBase));
        when(productRepository.existsBySkuAndDeletedAtIsNull("BC002")).thenReturn(true);

        assertThatThrownBy(() -> productService.update(1L, requestBase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("BC002");
    }

    // ─── RF-04 Eliminar producto (soft delete) ───────────────────────────────────

    @Test
    @DisplayName("RF-04 | Debe marcar deletedAt al eliminar un producto")
    void eliminar_productoExistente_marcaDeletedAt() {
        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoBase));
        when(productRepository.save(any(Product.class))).thenReturn(productoBase);

        productService.delete(1L);

        verify(productRepository, times(1)).save(argThat(p -> p.getDeletedAt() != null));
    }

    // ─── RF-18 Alerta de stock bajo ──────────────────────────────────────────────

    @Test
    @DisplayName("RF-18 | Debe retornar productos con stock bajo o igual al mínimo")
    void productosStockBajo_retornaListaCorrecta() {
        Product prodStockBajo = Product.builder()
                .id(2L).sku("BC002").name("Cable freno")
                .unitPrice(new BigDecimal("8000")).costPrice(new BigDecimal("5000"))
                .stock(3).minStock(5).unit("und").build();

        when(productRepository.findLowStock()).thenReturn(List.of(prodStockBajo));

        List<ProductResponse> result = productService.findLowStock();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSku()).isEqualTo("BC002");
        assertThat(result.get(0).isLowStock()).isTrue();
    }

    @Test
    @DisplayName("RF-18 | Producto con stock mayor al mínimo no debe estar en alerta")
    void productoConStockSuficiente_noEsLowStock() {
        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoBase));

        ProductResponse result = productService.findById(1L);

        assertThat(result.getStock()).isGreaterThan(result.getMinStock());
        assertThat(result.isLowStock()).isFalse();
    }

    // ─── Resolución de categorías ────────────────────────────────────────────────

    @Test
    @DisplayName("Debe asignar categoría válida al crear un producto")
    void crear_conCategoriaValida_asignaCategoria() {
        Category cat = Category.builder().id(1L).name("Frenos").build();
        requestBase.setCategoryIds(Set.of(1L));

        when(productRepository.existsBySkuAndDeletedAtIsNull("BC001")).thenReturn(false);
        when(categoryRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(cat));
        when(productRepository.save(any(Product.class))).thenReturn(productoBase);

        assertThatNoException().isThrownBy(() -> productService.create(requestBase));
        verify(categoryRepository, times(1)).findByIdAndDeletedAtIsNull(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la categoría asignada no existe")
    void crear_conCategoriaInexistente_lanzaExcepcion() {
        requestBase.setCategoryIds(Set.of(99L));

        when(productRepository.existsBySkuAndDeletedAtIsNull("BC001")).thenReturn(false);
        when(categoryRepository.findByIdAndDeletedAtIsNull(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.create(requestBase))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
