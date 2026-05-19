package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.MovementRequest;
import com.polidevtesis.inventory.dto.response.MovementResponse;
import com.polidevtesis.inventory.entity.InventoryMovement;
import com.polidevtesis.inventory.entity.MovementType;
import com.polidevtesis.inventory.entity.Product;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.InventoryMovementRepository;
import com.polidevtesis.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovementService - Pruebas Unitarias")
class MovementServiceTest {

    @Mock
    private InventoryMovementRepository movementRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private MovementService movementService;

    private Product productoCable;

    @BeforeEach
    void setUp() {
        productoCable = Product.builder()
                .id(1L)
                .sku("BC010")
                .name("Cable de freno bicicleta")
                .unitPrice(new BigDecimal("8000"))
                .costPrice(new BigDecimal("5000"))
                .stock(10)
                .minStock(5)
                .build();
    }

    // ─── RF-17 Registrar movimiento de entrada ───────────────────────────────────

    @Test
    @DisplayName("RF-17 | Debe registrar un movimiento de ENTRADA y aumentar el stock")
    void registrar_movimientoEntrada_aumentaStock() {
        int stockInicial = productoCable.getStock();
        int cantidad = 15;

        MovementRequest request = new MovementRequest();
        request.setProductId(1L);
        request.setType(MovementType.INPUT);
        request.setQuantity(cantidad);
        request.setReason("Orden de compra #201");
        request.setMovedAt(LocalDateTime.now());

        InventoryMovement movGuardado = InventoryMovement.builder()
                .id(1L).product(productoCable).type(MovementType.INPUT)
                .quantity(cantidad).reason("Orden de compra #201")
                .movedAt(LocalDateTime.now()).build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCable));
        when(productRepository.save(any(Product.class))).thenReturn(productoCable);
        when(movementRepository.save(any(InventoryMovement.class))).thenReturn(movGuardado);

        MovementResponse result = movementService.register(request);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(MovementType.INPUT);
        assertThat(productoCable.getStock()).isEqualTo(stockInicial + cantidad);
        verify(productRepository, times(1)).save(productoCable);
    }

    @Test
    @DisplayName("RF-17 | Debe registrar un movimiento de AJUSTE y actualizar el stock")
    void registrar_movimientoAjuste_actualizaStock() {
        int stockInicial = productoCable.getStock();
        int cantidad = 5;

        MovementRequest request = new MovementRequest();
        request.setProductId(1L);
        request.setType(MovementType.ADJUSTMENT);
        request.setQuantity(cantidad);
        request.setReason("Corrección de inventario físico");
        request.setMovedAt(LocalDateTime.now());

        InventoryMovement movGuardado = InventoryMovement.builder()
                .id(2L).product(productoCable).type(MovementType.ADJUSTMENT)
                .quantity(cantidad).reason("Corrección de inventario físico")
                .movedAt(LocalDateTime.now()).build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCable));
        when(productRepository.save(any(Product.class))).thenReturn(productoCable);
        when(movementRepository.save(any(InventoryMovement.class))).thenReturn(movGuardado);

        MovementResponse result = movementService.register(request);

        assertThat(result.getType()).isEqualTo(MovementType.ADJUSTMENT);
        assertThat(productoCable.getStock()).isEqualTo(stockInicial + cantidad);
    }

    @Test
    @DisplayName("RF-17 | Debe usar la fecha actual cuando no se especifica fecha de movimiento")
    void registrar_sinFecha_usaFechaActual() {
        MovementRequest request = new MovementRequest();
        request.setProductId(1L);
        request.setType(MovementType.INPUT);
        request.setQuantity(5);
        request.setReason("Sin fecha explícita");
        request.setMovedAt(null);

        InventoryMovement movGuardado = InventoryMovement.builder()
                .id(3L).product(productoCable).type(MovementType.INPUT)
                .quantity(5).movedAt(LocalDateTime.now()).build();

        when(productRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(productoCable));
        when(productRepository.save(any(Product.class))).thenReturn(productoCable);
        when(movementRepository.save(any(InventoryMovement.class))).thenReturn(movGuardado);

        MovementResponse result = movementService.register(request);

        assertThat(result.getMovedAt()).isNotNull();
    }

    // ─── RF-17 Validación: OUTPUT manual prohibido ───────────────────────────────

    @Test
    @DisplayName("RF-17 | Debe rechazar registro manual de movimiento de SALIDA (OUTPUT)")
    void registrar_movimientoSalidaManual_lanzaExcepcion() {
        MovementRequest request = new MovementRequest();
        request.setProductId(1L);
        request.setType(MovementType.OUTPUT);
        request.setQuantity(3);
        request.setReason("Intento manual");

        assertThatThrownBy(() -> movementService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("OUTPUT");

        verify(movementRepository, never()).save(any());
        verify(productRepository, never()).save(any());
    }

    // ─── RF-17 Producto no encontrado ───────────────────────────────────────────

    @Test
    @DisplayName("RF-17 | Debe lanzar excepción cuando el producto del movimiento no existe")
    void registrar_productoInexistente_lanzaExcepcion() {
        MovementRequest request = new MovementRequest();
        request.setProductId(99L);
        request.setType(MovementType.INPUT);
        request.setQuantity(10);

        when(productRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> movementService.register(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── Consultar movimiento por ID ─────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar movimiento existente por ID")
    void buscarPorId_movimientoExistente_retornaResponse() {
        InventoryMovement mov = InventoryMovement.builder()
                .id(7L).product(productoCable).type(MovementType.INPUT)
                .quantity(20).movedAt(LocalDateTime.now()).build();

        when(movementRepository.findById(7L)).thenReturn(Optional.of(mov));

        MovementResponse result = movementService.findById(7L);

        assertThat(result.getId()).isEqualTo(7L);
        assertThat(result.getType()).isEqualTo(MovementType.INPUT);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar movimiento con ID inexistente")
    void buscarPorId_movimientoInexistente_lanzaExcepcion() {
        when(movementRepository.findById(55L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movementService.findById(55L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("55");
    }

    // ─── Consultar movimientos por producto ──────────────────────────────────────

    @Test
    @DisplayName("Debe retornar historial de movimientos de un producto")
    void buscarPorProducto_retornaListaOrdenada() {
        InventoryMovement m1 = InventoryMovement.builder()
                .id(1L).product(productoCable).type(MovementType.INPUT)
                .quantity(20).movedAt(LocalDateTime.now().minusDays(5)).build();
        InventoryMovement m2 = InventoryMovement.builder()
                .id(2L).product(productoCable).type(MovementType.OUTPUT)
                .quantity(3).movedAt(LocalDateTime.now()).build();

        when(movementRepository.findByProductIdOrderByMovedAtAsc(1L))
                .thenReturn(List.of(m1, m2));

        List<MovementResponse> result = movementService.findByProduct(1L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getType()).isEqualTo(MovementType.INPUT);
        assertThat(result.get(1).getType()).isEqualTo(MovementType.OUTPUT);
    }

    // ─── createOutputMovement (llamado internamente por SaleService) ─────────────

    @Test
    @DisplayName("Debe crear movimiento de OUTPUT interno al registrar una venta")
    void crearMovimientoSalidaInterno_exitoso() {
        InventoryMovement movSalida = InventoryMovement.builder()
                .id(10L).product(productoCable).type(MovementType.OUTPUT)
                .quantity(2).reason("Sale #1").reference("SALE-1")
                .movedAt(LocalDateTime.now()).build();

        when(movementRepository.save(any(InventoryMovement.class))).thenReturn(movSalida);

        InventoryMovement result = movementService.createOutputMovement(
                productoCable, 2, "Sale #1", "SALE-1");

        assertThat(result.getType()).isEqualTo(MovementType.OUTPUT);
        assertThat(result.getQuantity()).isEqualTo(2);
        assertThat(result.getReason()).isEqualTo("Sale #1");
        verify(movementRepository, times(1)).save(any(InventoryMovement.class));
    }
}
