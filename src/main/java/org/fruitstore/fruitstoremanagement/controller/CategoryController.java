package org.fruitstore.fruitstoremanagement.controller;

import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.CategoryResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.CreateCategoryDTO;
import org.fruitstore.fruitstoremanagement.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        List<CategoryResponseDTO> responseDTOList = categoryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO) {
        CategoryResponseDTO responseDTO = categoryService.createCategory(createCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
