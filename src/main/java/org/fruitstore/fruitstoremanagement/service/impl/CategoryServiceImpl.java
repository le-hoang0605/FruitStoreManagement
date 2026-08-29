package org.fruitstore.fruitstoremanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.CategoryResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.CreateCategoryDTO;
import org.fruitstore.fruitstoremanagement.entity.Category;
import org.fruitstore.fruitstoremanagement.repository.CategoryRepository;
import org.fruitstore.fruitstoremanagement.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO> getCategories() {
        return categoryRepository.findAll().stream().map(
                category -> {
                    CategoryResponseDTO dto = new CategoryResponseDTO();
                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    dto.setDescription(category.getDescription());
                    dto.setCreatedAt(category.getCreatedAt());

                    //lazy loading problems here
                    int total = category.getFruits() != null ? category.getFruits().size() : 0;
                    dto.setTotalFruits(total);

                    return dto;
                }
        ).toList();
    }

    @Override
    public CategoryResponseDTO createCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = new Category();

        category.setName(createCategoryDTO.getName());
        category.setDescription(createCategoryDTO.getDescription());

        Category savedCategory = categoryRepository.save(category);

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(savedCategory.getId());
        dto.setName(savedCategory.getName());
        dto.setDescription(savedCategory.getDescription());
        dto.setCreatedAt(savedCategory.getCreatedAt());
        dto.setTotalFruits(0);

        return dto;
    }
}
