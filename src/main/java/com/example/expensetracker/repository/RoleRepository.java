package com.example.expensetracker.repository;
import com.example.expensetracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    // Returns Optional to handle case where role doesn't exist
}