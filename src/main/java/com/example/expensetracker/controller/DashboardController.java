package com.example.expensetracker.controller;

import com.example.expensetracker.service.DashboardService;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final ExpenseService expenseService;

    public DashboardController(DashboardService dashboardService, ExpenseService expenseService) {
        this.dashboardService = dashboardService;
        this.expenseService = expenseService;
    }

    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();

        model.addAttribute("monthlyTotal", dashboardService.getMonthlyTotal(email));
        model.addAttribute("expenseCount", dashboardService.getExpenseCount(email));
        model.addAttribute("expenses", expenseService.getAllExpensesForUser(email));
        model.addAttribute("categoryTotals", dashboardService.getCategoryTotals(email));

        return "dashboard/user-dashboard";
    }
}
