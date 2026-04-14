package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    // Interface defining all expense-related business operations

    void saveExpense(String userEmail, ExpenseDto expenseDto);
    // Creates a new expense for the specified user using data from ExpenseDto

    void updateExpense(Long expenseId, String userEmail, ExpenseDto expenseDto);
    // Updates an existing expense after verifying the user owns it

    void deleteExpense(Long expenseId, String userEmail);
    // Deletes an expense after verifying the user owns it

    ExpenseDto getExpenseById(Long expenseId, String userEmail);
    // Retrieves an expense by ID as a DTO, verifying user ownership

    List<Expense> getAllExpensesForUser(String userEmail);
    // Returns all expenses belonging to a specific user

    List<Expense> searchByCategory(String userEmail, Long categoryId);
    // Searches expenses by category ID for a specific user

    List<Expense> searchByDate(String userEmail, LocalDate date);
    // Searches expenses by exact date for a specific user

    List<Expense> searchByDateRange(String userEmail, LocalDate startDate, LocalDate endDate);
    // Searches expenses within a date range for a specific user

    List<Category> getAllCategories();
    // Returns all available categories (for dropdowns and filters)
}
