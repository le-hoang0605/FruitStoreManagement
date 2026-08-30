package org.fruitstore.fruitstoremanagement.service;

import org.fruitstore.fruitstoremanagement.dto.CreateFruitDTO;
import org.fruitstore.fruitstoremanagement.dto.FruitResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.UpdateFruitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FruitService {
    Page<FruitResponseDTO> getFruits(Pageable pageable);

    FruitResponseDTO getFruitById(UUID id);

    FruitResponseDTO createFruit(CreateFruitDTO createFruitDTO);

    FruitResponseDTO updateFruit(UUID id, UpdateFruitDTO updateFruitDTO);

    void deleteFruit(UUID id);
}
