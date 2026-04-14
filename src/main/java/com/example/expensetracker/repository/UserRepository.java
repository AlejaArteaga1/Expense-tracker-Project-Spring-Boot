package com.example.expensetracker.repository;
import com.example.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // Finds a user by their email address (which is used as username)
    // Returns Optional to handle case where email doesn't exist
    boolean existsByEmail(String email);
    // Checks if a user with the given email already exists in the database
    // Returns true if exists, false otherwise
}
