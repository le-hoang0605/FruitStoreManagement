package org.fruitstore.fruitstoremanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.CategoryResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.CreateFruitDTO;
import org.fruitstore.fruitstoremanagement.dto.FruitResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.UpdateFruitDTO;
import org.fruitstore.fruitstoremanagement.entity.Category;
import org.fruitstore.fruitstoremanagement.entity.Fruit;
import org.fruitstore.fruitstoremanagement.exception.ResourceNotFoundException;
import org.fruitstore.fruitstoremanagement.repository.CategoryRepository;
import org.fruitstore.fruitstoremanagement.repository.FruitRepository;
import org.fruitstore.fruitstoremanagement.service.FruitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FruitServiceImpl implements FruitService {
    private final FruitRepository fruitRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<FruitResponseDTO> getFruits(Pageable pageable) {
        return fruitRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public FruitResponseDTO getFruitById(UUID id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));
        return mapToDTO(fruit);
    }

    @Override
    public FruitResponseDTO createFruit(CreateFruitDTO createFruitDTO) {
        Category category = categoryRepository.findById(createFruitDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + createFruitDTO.getCategoryId()));

        Fruit fruit = new Fruit();
        fruit.setName(createFruitDTO.getName());
        fruit.setPrice(createFruitDTO.getPrice());
        fruit.setStockQuantity(createFruitDTO.getStockQuantity());
        fruit.setCategory(category);

        Fruit savedFruit = fruitRepository.save(fruit);
        return mapToDTO(savedFruit);
    }

    @Override
    public FruitResponseDTO updateFruit(UUID id, UpdateFruitDTO updateFruitDTO) {
        Fruit existingFruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));

        Category category = categoryRepository.findById(updateFruitDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + updateFruitDTO.getCategoryId()));

        existingFruit.setName(updateFruitDTO.getName());
        existingFruit.setPrice(updateFruitDTO.getPrice());
        existingFruit.setStockQuantity(updateFruitDTO.getStockQuantity());
        existingFruit.setCategory(category);

        Fruit updatedFruit = fruitRepository.save(existingFruit);
        return mapToDTO(updatedFruit);
    }

    @Override
    public void deleteFruit(UUID id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));

        fruitRepository.delete(fruit);
    }

    private FruitResponseDTO mapToDTO(Fruit fruit) {
        FruitResponseDTO dto = new FruitResponseDTO();

        dto.setId(fruit.getId());
        dto.setName(fruit.getName());
        dto.setPrice(fruit.getPrice());
        dto.setStockQuantity(fruit.getStockQuantity());

        if (fruit.getCategory() != null) {
            CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
            categoryDTO.setId(fruit.getCategory().getId());
            categoryDTO.setName(fruit.getCategory().getName());
            categoryDTO.setDescription(fruit.getCategory().getDescription());
            dto.setCategory(categoryDTO);
        }
        return dto;
    }
}
