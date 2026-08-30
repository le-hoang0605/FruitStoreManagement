package org.fruitstore.fruitstoremanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.CreateFruitDTO;
import org.fruitstore.fruitstoremanagement.dto.FruitResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.UpdateFruitDTO;
import org.fruitstore.fruitstoremanagement.service.FruitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/fruits")
@RequiredArgsConstructor
public class FruitController {
    private final FruitService fruitService;

    @GetMapping
    public ResponseEntity<Page<FruitResponseDTO>> getFruits(Pageable pageable) {
        Page<FruitResponseDTO> fruits = fruitService.getFruits(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(fruits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitResponseDTO> getFruitById(@PathVariable UUID id) {
        FruitResponseDTO fruitResponseDTO = fruitService.getFruitById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fruitResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FruitResponseDTO> createFruit(@Valid @RequestBody CreateFruitDTO createFruitDTO) {
        FruitResponseDTO fruitResponseDTO = fruitService.createFruit(createFruitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(fruitResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FruitResponseDTO> updateFruit(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFruitDTO updateFruitDTO
    ) {
        FruitResponseDTO fruitResponseDTO = fruitService.updateFruit(id, updateFruitDTO);
        return ResponseEntity.status(HttpStatus.OK).body(fruitResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteFruit(@PathVariable UUID id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.ok(Map.of("message", "Delete successfully!"));
    }
}
