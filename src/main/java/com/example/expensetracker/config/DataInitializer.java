package com.example.expensetracker.config;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Role;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.RoleRepository;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(List.of(
                    new Category("Food", "Food and groceries"),
                    new Category("Travel", "Transportation and travel"),
                    new Category("Bills", "Utility and service bills"),
                    new Category("Shopping", "Shopping expenses"),
                    new Category("Health", "Medical and health expenses"),
                    new Category("Entertainment", "Movies, fun, hobbies"),
                    new Category("Other", "Other expenses")
            ));
        }

        if (!userRepository.existsByEmail("admin@expensetracker.com")) {
            User admin = new User();
            admin.setFullName("System Administrator");
            admin.setEmail("admin@expensetracker.com");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setEnabled(true);
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userRepository.save(admin);
        }
    }
}