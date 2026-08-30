package org.fruitstore.fruitstoremanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class FruitResponseDTO {
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;

    private CategoryResponseDTO category;
}
