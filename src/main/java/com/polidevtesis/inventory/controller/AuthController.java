package com.polidevtesis.inventory.controller;

import com.polidevtesis.inventory.dto.request.LoginRequest;
import com.polidevtesis.inventory.dto.response.ApiResponse;
import com.polidevtesis.inventory.dto.response.LoginResponse;
import com.polidevtesis.inventory.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.login(request)));
    }
}
