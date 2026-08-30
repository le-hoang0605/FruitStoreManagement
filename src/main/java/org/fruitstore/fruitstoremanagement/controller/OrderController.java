package org.fruitstore.fruitstoremanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.CreateOrderDTO;
import org.fruitstore.fruitstoremanagement.dto.OrderResponseDTO;
import org.fruitstore.fruitstoremanagement.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(Pageable pageable) {
        Page<OrderResponseDTO> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderDTO createOrderDTO) {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(createOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<OrderResponseDTO>> getOrderByUserId(Pageable pageable,
                                                                   @PathVariable UUID userId) {
        Page<OrderResponseDTO> orderResponse = orderService.getOrderByUserId(pageable, userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }
}
