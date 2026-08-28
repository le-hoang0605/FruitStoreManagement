package org.fruitstore.fruitstoremanagement.repository;

import org.fruitstore.fruitstoremanagement.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
