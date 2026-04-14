package com.example.expensetracker.service;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {
    // Interface defining all dashboard-related business operations

    BigDecimal getMonthlyTotal(String userEmail);
    // Returns total expenses for the current month for a specific user

    long getExpenseCount(String userEmail);
    // Returns total number of expense entries for a specific user

    long getTotalUsers();
    // Returns total number of registered users in the system (admin only)

    long getTotalExpenses();
    // Returns total number of expense records across all users (admin only)

    BigDecimal getSystemTotalAmount();
    // Returns sum of all expense amounts across the entire system (admin only)

    Map<String, BigDecimal> getCategoryTotals(String userEmail);
    // Returns a map where key = category name, value = total amount spent in that category
    // Example: {"Food": 250.50, "Travel": 120.00, "Bills": 300.00}
}
