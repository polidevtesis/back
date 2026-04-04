package com.polidevtesis.inventory.controller;

import com.polidevtesis.inventory.dto.request.ProviderRequest;
import com.polidevtesis.inventory.dto.response.ApiResponse;
import com.polidevtesis.inventory.dto.response.ProviderResponse;
import com.polidevtesis.inventory.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProviderResponse>>> findAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(providerService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProviderResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(providerService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProviderResponse>> create(@Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(providerService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProviderResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(providerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Provider deleted"));
    }
}
