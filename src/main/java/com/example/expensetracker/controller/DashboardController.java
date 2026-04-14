package com.example.expensetracker.controller;
import com.example.expensetracker.service.DashboardService;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// Marks this class as a Spring MVC controller for user dashboard functionality
public class DashboardController {

    private final DashboardService dashboardService;
    // Provides aggregated statistics for the dashboard (totals, counts, category sums)

    private final ExpenseService expenseService;
    // Provides expense data retrieval for the logged-in user

    public DashboardController(DashboardService dashboardService, ExpenseService expenseService) {
        this.dashboardService = dashboardService;
        this.expenseService = expenseService;
        // Constructor-based dependency injection for both services
    }

    @GetMapping("/dashboard")
    // Maps HTTP GET requests to "/dashboard" to display the user's personal dashboard
    public String userDashboard(Authentication authentication, Model model) {
        // Authentication object contains the currently logged-in user's details
        String email = authentication.getName();
        // Extracts the user's email (username) from the authentication object

        model.addAttribute("monthlyTotal", dashboardService.getMonthlyTotal(email));
        // Adds total expenses for the current month for this specific user

        model.addAttribute("expenseCount", dashboardService.getExpenseCount(email));
        // Adds total number of expense entries made by this user

        model.addAttribute("expenses", expenseService.getAllExpensesForUser(email));
        // Adds the list of all expenses belonging to this user

        model.addAttribute("categoryTotals", dashboardService.getCategoryTotals(email));
        // Adds a map of category names to their total amounts for this user

        return "dashboard/user-dashboard";
        // Returns the "dashboard/user-dashboard.html" template
    }
}

