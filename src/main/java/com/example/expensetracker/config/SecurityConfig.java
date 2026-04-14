package com.example.expensetracker.config;
import com.example.expensetracker.service.CustomUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Marks this class as a configuration class that defines Spring beans
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    // Service that loads user-specific data during authentication

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    // Registers this as a Spring bean - creates BCrypt password encoder
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // BCrypt is a strong hashing algorithm for passwords (includes salt automatically)
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider uses UserDetailsService to authenticate users
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());  // Tell it how to verify passwords
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configures Spring Security's filter chain - main security configuration
        http
                .authenticationProvider(authenticationProvider())
                // Set custom authentication provider

                .authorizeHttpRequests(auth -> auth
                        // Public URLs - anyone can access these without login
                        .requestMatchers("/", "/register", "/login", "/css/**").permitAll()

                        // Admin-only URLs - requires ROLE_ADMIN
                        .requestMatchers("/templates/admin/**").hasRole("ADMIN")

                        // User/Admin URLs - accessible to both authenticated users and admins
                        .requestMatchers("/expenses/**", "/dashboard/**").hasAnyRole("USER", "ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page URL
                        .defaultSuccessUrl("/dashboard", true)  // Redirect after successful login
                        .permitAll()  // Everyone can access login page
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")  // Redirect after logout with message
                        .permitAll()
                )

                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
        // Redirect to custom access denied page when user lacks permissions

        return http.build();
    }
}