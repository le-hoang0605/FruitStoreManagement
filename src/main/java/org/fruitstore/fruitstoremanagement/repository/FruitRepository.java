package org.fruitstore.fruitstoremanagement.repository;

import org.fruitstore.fruitstoremanagement.entity.Fruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, UUID> {
    List<Fruit> findByCategoryId(UUID categoryId);

    Page<Fruit> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
