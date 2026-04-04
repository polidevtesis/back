package com.polidevtesis.inventory.controller;

import com.polidevtesis.inventory.ai.ClaudeOrderService;
import com.polidevtesis.inventory.dto.request.OrderRecommendationRequest;
import com.polidevtesis.inventory.dto.response.ApiResponse;
import com.polidevtesis.inventory.dto.response.OrderRecommendationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final ClaudeOrderService claudeOrderService;

    /**
     * POST /api/v1/ai/order-recommendation
     *
     * Requires:
     *   - Authorization: Bearer <jwt>           (standard auth)
     *   - X-Claude-Api-Key: <anthropic key>     (provided ad-hoc, never stored)
     */
    @PostMapping("/order-recommendation")
    public ResponseEntity<ApiResponse<OrderRecommendationResponse>> recommend(
            @RequestHeader("X-Claude-Api-Key") String claudeApiKey,
            @Valid @RequestBody OrderRecommendationRequest request) {

        if (claudeApiKey == null || claudeApiKey.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("X-Claude-Api-Key header is required", "MISSING_API_KEY"));
        }

        OrderRecommendationResponse result = claudeOrderService.recommend(claudeApiKey, request);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
