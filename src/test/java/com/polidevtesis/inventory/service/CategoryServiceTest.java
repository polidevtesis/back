package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.CategoryRequest;
import com.polidevtesis.inventory.dto.response.CategoryResponse;
import com.polidevtesis.inventory.entity.Category;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService - Pruebas Unitarias")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category categoriaFreno;
    private CategoryRequest requestFreno;

    @BeforeEach
    void setUp() {
        categoriaFreno = Category.builder()
                .id(1L)
                .name("Frenos")
                .description("Repuestos del sistema de frenos")
                .build();

        requestFreno = new CategoryRequest();
        requestFreno.setName("Frenos");
        requestFreno.setDescription("Repuestos del sistema de frenos");
    }

    // ─── RF-05 Crear categoría ───────────────────────────────────────────────────

    @Test
    @DisplayName("RF-05 | Debe crear una categoría correctamente")
    void crear_categoriaValida_exitoso() {
        when(categoryRepository.save(any(Category.class))).thenReturn(categoriaFreno);

        CategoryResponse result = categoryService.create(requestFreno);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Frenos");
        assertThat(result.getDescription()).isEqualTo("Repuestos del sistema de frenos");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("RF-05 | Debe persistir la descripción cuando se provee")
    void crear_conDescripcion_persisteDescripcion() {
        when(categoryRepository.save(any(Category.class))).thenReturn(categoriaFreno);

        CategoryResponse result = categoryService.create(requestFreno);

        assertThat(result.getDescription()).isNotBlank();
    }

    // ─── RF-06 Listar categorías ─────────────────────────────────────────────────

    @Test
    @DisplayName("RF-06 | Debe retornar lista de categorías activas")
    void listar_todasCategoriasActivas_retornaLista() {
        Category cat2 = Category.builder().id(2L).name("Transmisión").build();
        when(categoryRepository.findAllByDeletedAtIsNull()).thenReturn(List.of(categoriaFreno, cat2));

        List<CategoryResponse> result = categoryService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CategoryResponse::getName)
                .containsExactlyInAnyOrder("Frenos", "Transmisión");
    }

    @Test
    @DisplayName("RF-06 | Debe retornar lista vacía cuando no hay categorías")
    void listar_sinCategorias_retornaListaVacia() {
        when(categoryRepository.findAllByDeletedAtIsNull()).thenReturn(List.of());

        List<CategoryResponse> result = categoryService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("RF-06 | Debe retornar categoría por ID cuando existe")
    void buscarPorId_categoriaExistente_retornaResponse() {
        when(categoryRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(categoriaFreno));

        CategoryResponse result = categoryService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Frenos");
    }

    @Test
    @DisplayName("RF-06 | Debe lanzar excepción cuando la categoría no existe")
    void buscarPorId_categoriaNoExistente_lanzaExcepcion() {
        when(categoryRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── RF-07 Actualizar categoría ──────────────────────────────────────────────

    @Test
    @DisplayName("RF-07 | Debe actualizar nombre y descripción de una categoría")
    void actualizar_categoriaExistente_exitoso() {
        CategoryRequest requestActualizado = new CategoryRequest();
        requestActualizado.setName("Frenos y Suspensión");
        requestActualizado.setDescription("Sistema de frenado completo");

        Category categoriaActualizada = Category.builder()
                .id(1L).name("Frenos y Suspensión")
                .description("Sistema de frenado completo").build();

        when(categoryRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(categoriaFreno));
        when(categoryRepository.save(any(Category.class))).thenReturn(categoriaActualizada);

        CategoryResponse result = categoryService.update(1L, requestActualizado);

        assertThat(result.getName()).isEqualTo("Frenos y Suspensión");
        assertThat(result.getDescription()).isEqualTo("Sistema de frenado completo");
    }

    @Test
    @DisplayName("RF-07 | Debe lanzar excepción al actualizar categoría inexistente")
    void actualizar_categoriaInexistente_lanzaExcepcion() {
        when(categoryRepository.findByIdAndDeletedAtIsNull(50L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.update(50L, requestFreno))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // ─── RF-08 Eliminar categoría (soft delete) ──────────────────────────────────

    @Test
    @DisplayName("RF-08 | Debe marcar deletedAt al eliminar una categoría")
    void eliminar_categoriaExistente_marcaDeletedAt() {
        when(categoryRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(categoriaFreno));
        when(categoryRepository.save(any(Category.class))).thenReturn(categoriaFreno);

        categoryService.delete(1L);

        verify(categoryRepository, times(1))
                .save(argThat(c -> c.getDeletedAt() != null));
    }

    @Test
    @DisplayName("RF-08 | Debe lanzar excepción al eliminar categoría inexistente")
    void eliminar_categoriaInexistente_lanzaExcepcion() {
        when(categoryRepository.findByIdAndDeletedAtIsNull(88L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.delete(88L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(categoryRepository, never()).save(any());
    }
}
