package com.example.expensetracker.service;

import com.example.expensetracker.dto.RegisterDto;
import com.example.expensetracker.entity.User;

public interface UserServices {
    void registerUser(RegisterDto registerDto);
    User findByEmail(String email);
}