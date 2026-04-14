package com.example.expensetracker.controller;
import com.example.expensetracker.dto.RegisterDto;
import com.example.expensetracker.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
// Marks this class as a Spring MVC controller for authentication-related requests
public class AuthController {

    private final UserServiceImpl userService;
    // Injects the UserService implementation to handle user registration logic

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
        // Constructor-based dependency injection for UserService
    }

    @GetMapping("/register")
    // Maps HTTP GET requests to "/register" to display the registration form
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        // Creates and adds an empty RegisterDto object to bind form input fields
        return "auth/register";
        // Returns the "auth/register.html" Thymeleaf template
    }

    @PostMapping("/register")
    // Maps HTTP POST requests to "/register" to process form submission
    public String registerUser(@Valid @ModelAttribute("registerDto") RegisterDto registerDto,
                               // @Valid triggers validation annotations on RegisterDto
                               // @ModelAttribute binds form data to the RegisterDto object
                               BindingResult result,
                               // Captures validation errors if any
                               Model model) {
        // Allows adding error messages to the view

        if (result.hasErrors()) {
            // Checks if validation failed (e.g., empty fields, invalid email format)
            return "auth/register";
            // Returns to registration form to display error messages
        }

        try {
            userService.registerUser(registerDto);
            // Attempts to create a new user account with the provided data
        } catch (RuntimeException ex) {
            // Catches registration errors like duplicate email or weak password
            model.addAttribute("errorMessage", ex.getMessage());
            // Adds the error message to the model to display on the form
            return "auth/register";
            // Returns to registration form with the error message
        }

        return "redirect:/login?registered";
        // Redirects to login page with success query parameter after successful registration
    }

    @GetMapping("/login")
    // Maps HTTP GET requests to "/login" to display the login page
    public String showLoginPage() {
        return "auth/login";
        // Returns the "auth/login.html" template - Spring Security handles actual authentication
    }
}