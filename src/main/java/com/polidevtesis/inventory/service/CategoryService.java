package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.CategoryRequest;
import com.polidevtesis.inventory.dto.response.CategoryResponse;
import com.polidevtesis.inventory.entity.Category;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllByDeletedAtIsNull().stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        return CategoryResponse.from(getActiveOrThrow(id));
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return CategoryResponse.from(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getActiveOrThrow(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    public void delete(Long id) {
        Category category = getActiveOrThrow(id);
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    private Category getActiveOrThrow(Long id) {
        return categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Category not found with id: " + id, "CATEGORY_NOT_FOUND"));
    }
}
