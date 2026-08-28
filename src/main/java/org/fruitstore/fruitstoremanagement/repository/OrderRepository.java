package org.fruitstore.fruitstoremanagement.repository;

import org.fruitstore.fruitstoremanagement.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository {
    List<Order> findByUserId(UUID userId);

    Page<Order> findByUserId(UUID userId, Pageable pageable);
}
