package org.fruitstore.fruitstoremanagement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.JwtAuthResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.LoginRequestDTO;
import org.fruitstore.fruitstoremanagement.dto.RegisterRequestDTO;
import org.fruitstore.fruitstoremanagement.dto.UserResponseDTO;
import org.fruitstore.fruitstoremanagement.entity.Role;
import org.fruitstore.fruitstoremanagement.entity.User;
import org.fruitstore.fruitstoremanagement.exception.EmailExistsException;
import org.fruitstore.fruitstoremanagement.repository.UserRepository;
import org.fruitstore.fruitstoremanagement.security.JwtTokenProvider;
import org.fruitstore.fruitstoremanagement.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserResponseDTO register(RegisterRequestDTO registerRequest) {
        if (repo.existsByEmail(registerRequest.getEmail()))
            throw new EmailExistsException("Email already exists");
        var user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole(Role.ROLE_USER);
        user.setEmail(registerRequest.getEmail());

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = repo.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getFirstName() + " " + savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );
    }

    @Override
    public JwtAuthResponseDTO login(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = repo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(user);
        return new JwtAuthResponseDTO(token);
    }
}
