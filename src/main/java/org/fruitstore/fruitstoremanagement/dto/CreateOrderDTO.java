package org.fruitstore.fruitstoremanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderDTO {
    @NotEmpty
    private List<CreateOrderItemDTO> items;
}
