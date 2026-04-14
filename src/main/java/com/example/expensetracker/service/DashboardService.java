package com.example.expensetracker.service;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {
    BigDecimal getMonthlyTotal(String userEmail);
    long getExpenseCount(String userEmail);
    long getTotalUsers();
    long getTotalExpenses();
    BigDecimal getSystemTotalAmount();
    Map<String, BigDecimal> getCategoryTotals(String userEmail);
}
