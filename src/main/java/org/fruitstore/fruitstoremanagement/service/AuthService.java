package org.fruitstore.fruitstoremanagement.service;

import org.fruitstore.fruitstoremanagement.dto.JwtAuthResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.LoginRequestDTO;
import org.fruitstore.fruitstoremanagement.dto.RegisterRequestDTO;
import org.fruitstore.fruitstoremanagement.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO registerRequestDTO);

    JwtAuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}
