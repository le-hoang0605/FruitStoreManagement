package org.fruitstore.fruitstoremanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrderItemDTO {
    @NotNull
    private UUID fruitId;

    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
