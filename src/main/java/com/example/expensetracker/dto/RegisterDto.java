package com.example.expensetracker.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Data Transfer Object for transferring registration data from form to service
public class RegisterDto {

    @NotBlank(message = "Full name is required.")
    // Ensures full name is provided (not null, empty, or whitespace)
    private String fullName;

    @NotBlank(message = "Email is required.")
    // Ensures email field is not empty
    @Email(message = "Invalid email format.")
    // Validates that the string follows email format (e.g., user@example.com)
    private String email;

    @NotBlank(message = "Password is required.")
    // Ensures password field is not empty
    @Size(min = 6, message = "Password must have at least 6 characters.")
    // Ensures password length is at least 6 characters for security
    private String password;
}
