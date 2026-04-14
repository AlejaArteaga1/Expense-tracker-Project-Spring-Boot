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
// Registers this class as a Spring bean that runs at application startup
public class DataInitializer implements CommandLineRunner {
    // CommandLineRunner executes code after the Spring context is fully loaded

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, CategoryRepository categoryRepository,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // This method runs automatically when the application starts

        // Create or retrieve ROLE_USER - existsOrCreate pattern
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        // If ROLE_USER doesn't exist, create and save it; otherwise use existing

        // Create or retrieve ROLE_ADMIN
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        // Only seed categories if none exist in database
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

        // Create default admin user only if it doesn't already exist
        if (!userRepository.existsByEmail("admin@expensetracker.com")) {
            User admin = new User();
            admin.setFullName("System Administrator");
            admin.setEmail("admin@expensetracker.com");
            admin.setPassword(passwordEncoder.encode("Admin123"));  // Hash password before storing
            admin.setEnabled(true);  // Account is active
            admin.setRoles(Set.of(roleAdmin, roleUser));  // Assign both ADMIN and USER roles
            userRepository.save(admin);
        }
    }
}