package com.example.expensetracker.service.impl;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.DashboardService;
import com.example.expensetracker.service.UserService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public DashboardServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public BigDecimal getMonthlyTotal(String userEmail) {
        User user = userService.findByEmail(userEmail);
        LocalDate now = LocalDate.now();
        return expenseRepository.getMonthlyTotal(user, now.getYear(), now.getMonthValue());
    }

    @Override
    public long getExpenseCount(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return expenseRepository.countByUser(user);
    }

    @Override
    public long getTotalUsers() {
        return userRepository.count();
    }

    @Override
    public long getTotalExpenses() {
        return expenseRepository.count();
    }

    @Override
    public BigDecimal getSystemTotalAmount() {
        return expenseRepository.getSystemTotalAmount();
    }

    @Override
    public Map<String, BigDecimal> getCategoryTotals(String userEmail) {
        User user = userService.findByEmail(userEmail);
        List<Expense> expenses = expenseRepository.findByUserOrderByExpenseDateDesc(user);

        Map<String, BigDecimal> totals = new LinkedHashMap<>();

        for (Expense expense : expenses) {
            String categoryName = expense.getCategory().getName();
            BigDecimal current = totals.getOrDefault(categoryName, BigDecimal.ZERO);
            totals.put(categoryName, current.add(expense.getAmount()));
        }

        return totals;
    }
}
