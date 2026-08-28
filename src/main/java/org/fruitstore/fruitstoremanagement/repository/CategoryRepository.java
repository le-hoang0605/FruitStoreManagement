package org.fruitstore.fruitstoremanagement.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository {
    boolean existsByName(String name);
}
