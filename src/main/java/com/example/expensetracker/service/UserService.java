package com.example.expensetracker.service;
import com.example.expensetracker.dto.RegisterDto;
import com.example.expensetracker.entity.User;

public interface UserService {
    // Interface defining user-related business operations

    void registerUser(RegisterDto registerDto);
    // Creates a new user account from registration data
    // Throws exception if email already exists or validation fails
    // Encrypts password before saving to database

    User findByEmail(String email);
    // Finds and returns a User entity by email address
    // Used for retrieving user details after authentication
}