package org.example.rideshare.repository;

import org.example.rideshare.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity operations.
 * Spring Data MongoDB handles the implementation automatically.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Find user by username - used for login and duplicate checks
    Optional<User> findByUsername(String username);

    // Check if username is already taken
    boolean existsByUsername(String username);
}
