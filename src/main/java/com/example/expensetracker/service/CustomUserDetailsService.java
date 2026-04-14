package com.example.expensetracker.service;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
// Registers this class as a Spring service bean for dependency injection
public class CustomUserDetailsService implements UserDetailsService {
    // UserDetailsService is Spring Security's interface for loading user data during authentication

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Spring Security calls this method automatically during login
        // It loads user details by email (username) for authentication

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        // Fetch user from database or throw exception if not found

        // Convert our User entity to Spring Security's UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),      // Username (email)
                user.getPassword(),   // Hashed password for verification
                user.isEnabled(),     // Account active status
                true,                 // Account not expired
                true,                 // Credentials not expired
                true,                 // Account not locked
                user.getRoles()       // Authorities (roles) assigned to user
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        // Convert each Role entity to SimpleGrantedAuthority (e.g., "ROLE_USER")
                        .collect(Collectors.toSet())
        );
    }
}