package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    void saveExpense(String userEmail, ExpenseDto expenseDto);
    void updateExpense(Long expenseId, String userEmail, ExpenseDto expenseDto);
    void deleteExpense(Long expenseId, String userEmail);
    ExpenseDto getExpenseById(Long expenseId, String userEmail);
    List<Expense> getAllExpensesForUser(String userEmail);
    List<Expense> searchByCategory(String userEmail, Long categoryId);
    List<Expense> searchByDate(String userEmail, LocalDate date);
    List<Expense> searchByDateRange(String userEmail, LocalDate startDate, LocalDate endDate);
    List<Category> getAllCategories();
}
