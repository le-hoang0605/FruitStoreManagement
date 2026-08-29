package org.fruitstore.fruitstoremanagement.service;

import org.fruitstore.fruitstoremanagement.dto.CategoryResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.CreateCategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getCategories();

    CategoryResponseDTO createCategory(CreateCategoryDTO createCategoryDTO);
}
