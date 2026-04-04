package com.polidevtesis.inventory.controller;

import com.polidevtesis.inventory.dto.request.MovementRequest;
import com.polidevtesis.inventory.dto.response.ApiResponse;
import com.polidevtesis.inventory.dto.response.MovementResponse;
import com.polidevtesis.inventory.entity.MovementType;
import com.polidevtesis.inventory.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MovementResponse>>> search(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) MovementType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @PageableDefault(size = 20, sort = "movedAt") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(
            movementService.search(productId, type, from, to, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovementResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(movementService.findById(id)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<MovementResponse>>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.ok(movementService.findByProduct(productId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovementResponse>> register(@Valid @RequestBody MovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(movementService.register(request)));
    }
}
