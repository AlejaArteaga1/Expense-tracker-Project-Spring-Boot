package com.example.expensetracker.controller;

import com.example.expensetracker.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// Marks this class as a Spring MVC controller that handles HTTP requests
public class AdminController {

    private final DashboardService dashboardService;
    // Injects the DashboardService to provide system-wide statistics for admin view

    public AdminController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        // Constructor-based dependency injection for DashboardService
    }

    @GetMapping("/admin/dashboard")
    // Maps HTTP GET requests to "/admin/dashboard" to this method
    public String adminDashboard(Model model) {
        // Model object carries data from controller to the view template

        model.addAttribute("totalUsers", dashboardService.getTotalUsers());
        // Adds total number of registered users to the model under key "totalUsers"

        model.addAttribute("totalExpenses", dashboardService.getTotalExpenses());
        // Adds total count of all expense records across the system to the model

        model.addAttribute("systemTotalAmount", dashboardService.getSystemTotalAmount());
        // Adds sum of all expense amounts across all users to the model

        return "admin/dashboard";
        // Returns the view template name "admin/dashboard.html" to be rendered
    }
}