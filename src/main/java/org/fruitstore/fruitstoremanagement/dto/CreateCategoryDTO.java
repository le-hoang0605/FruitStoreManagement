package org.fruitstore.fruitstoremanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {
    @NotBlank
    private String name;
    @Size(max = 255)
    private String description;
}
