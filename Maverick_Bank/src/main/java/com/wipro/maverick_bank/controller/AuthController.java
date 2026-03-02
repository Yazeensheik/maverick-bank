package com.wipro.maverick_bank.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.maverick_bank.dto.LoginRequestDTO;
import com.wipro.maverick_bank.dto.LoginResponseDTO;
import com.wipro.maverick_bank.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login API
     * Accessible by: Customer, Employee, Admin
     * Security: PUBLIC (no JWT required)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequest) {

        LoginResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}