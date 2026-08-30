package org.fruitstore.fruitstoremanagement.service;

import org.fruitstore.fruitstoremanagement.dto.CreateOrderDTO;
import org.fruitstore.fruitstoremanagement.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);

    OrderResponseDTO createOrder(CreateOrderDTO createOrderDTO);

    Page<OrderResponseDTO> getOrderByUserId(Pageable pageable, UUID userId);
}
