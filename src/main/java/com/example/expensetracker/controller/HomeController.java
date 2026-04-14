package com.example.expensetracker.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// Marks this class as a Spring MVC controller for public/home pages
public class HomeController {

    @GetMapping("/")
    // Handles GET requests to root URL "/" - the landing page
    public String home() {
        return "index";  // Returns index.html template (public homepage)
    }

    @GetMapping("/access-denied")
    // Handles GET requests to "/access-denied" - error page for unauthorized access
    public String accessDenied() {
        return "access-denied";  // Returns access-denied.html template
        // Spring Security automatically redirects here when user lacks required role
    }
}