package org.fruitstore.fruitstoremanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.JwtAuthResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.LoginRequestDTO;
import org.fruitstore.fruitstoremanagement.dto.RegisterRequestDTO;
import org.fruitstore.fruitstoremanagement.dto.UserResponseDTO;
import org.fruitstore.fruitstoremanagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        JwtAuthResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
