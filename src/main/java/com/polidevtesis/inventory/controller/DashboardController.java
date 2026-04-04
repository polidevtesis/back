package com.polidevtesis.inventory.controller;

import com.polidevtesis.inventory.dto.response.ApiResponse;
import com.polidevtesis.inventory.dto.response.DashboardSummaryResponse;
import com.polidevtesis.inventory.dto.response.MovementResponse;
import com.polidevtesis.inventory.dto.response.ProductResponse;
import com.polidevtesis.inventory.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getSummary(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(ApiResponse.ok(dashboardService.getSummary(from, to)));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getLowStock() {
        return ResponseEntity.ok(ApiResponse.ok(dashboardService.getLowStock()));
    }

    @GetMapping("/sales-by-category")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSalesByCategory(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(ApiResponse.ok(dashboardService.getSalesByCategory(from, to)));
    }

    @GetMapping("/top-products")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopProducts(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.ok(dashboardService.getTopProducts(from, to, limit)));
    }

    @GetMapping("/stock-history/{productId}")
    public ResponseEntity<ApiResponse<List<MovementResponse>>> getStockHistory(
            @PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.ok(dashboardService.getStockHistory(productId)));
    }
}
