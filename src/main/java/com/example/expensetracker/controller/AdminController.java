package com.example.expensetracker.controller;

import com.example.expensetracker.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final DashboardService dashboardService;

    public AdminController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/templates/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", dashboardService.getTotalUsers());
        model.addAttribute("totalExpenses", dashboardService.getTotalExpenses());
        model.addAttribute("systemTotalAmount", dashboardService.getSystemTotalAmount());

        return "templates/admin/dashboard";
    }
}
