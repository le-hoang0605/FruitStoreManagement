package org.fruitstore.fruitstoremanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.fruitstore.fruitstoremanagement.dto.CreateOrderDTO;
import org.fruitstore.fruitstoremanagement.dto.CreateOrderItemDTO;
import org.fruitstore.fruitstoremanagement.dto.OrderItemResponseDTO;
import org.fruitstore.fruitstoremanagement.dto.OrderResponseDTO;
import org.fruitstore.fruitstoremanagement.entity.*;
import org.fruitstore.fruitstoremanagement.exception.ResourceNotFoundException;
import org.fruitstore.fruitstoremanagement.repository.FruitRepository;
import org.fruitstore.fruitstoremanagement.repository.OrderRepository;
import org.fruitstore.fruitstoremanagement.repository.UserRepository;
import org.fruitstore.fruitstoremanagement.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FruitRepository fruitRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO createOrderDTO) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(currentUser);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemDTO itemDTO : createOrderDTO.getItems()) {
            Fruit fruit = fruitRepository.findById(itemDTO.getFruitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + itemDTO.getFruitId()));

            if (fruit.getStockQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Item '" + fruit.getName() + "' does not have enough (Stock: " + fruit.getStockQuantity() + ")");
            }

            fruit.setStockQuantity(fruit.getStockQuantity() - itemDTO.getQuantity());
            fruitRepository.save(fruit);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFruit(fruit);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(fruit.getPrice());

            BigDecimal itemTotal = fruit.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            orderItems.add(orderItem);
        }
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrderByUserId(Pageable pageable, UUID userId) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = currentUser.getRole() == Role.ROLE_ADMIN;
        if (!isAdmin && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized!");
        }

        return orderRepository.findByUserId(userId, pageable)
                .map(this::mapToDTO);
    }

    private OrderResponseDTO mapToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setOrderDate(order.getOrderDate());

        List<OrderItemResponseDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setId(item.getId());
            itemDTO.setFruitId(item.getFruit().getId());
            itemDTO.setFruitName(item.getFruit().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setUnitPrice(item.getUnitPrice());
            itemDTO.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemDTO;
        }).toList();

        dto.setItems(itemDTOs);
        return dto;
    }
}
