package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.LoginRequest;
import com.polidevtesis.inventory.dto.response.LoginResponse;
import com.polidevtesis.inventory.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtService.generateToken(request.getUsername());
        return new LoginResponse(token, jwtService.getExpirationMs() / 1000);
    }
}
