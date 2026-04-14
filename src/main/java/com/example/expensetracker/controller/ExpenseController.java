package com.example.expensetracker.controller;
import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
// Marks this class as a Spring MVC controller
@RequestMapping("/expenses")
// All URLs in this controller will be prefixed with "/expenses"
public class ExpenseController {

    private final ExpenseService expenseService;
    // Service layer for expense business logic

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    // Handles GET requests to "/expenses" - list all expenses for logged-in user
    public String listExpenses(Authentication authentication, Model model) {
        String email = authentication.getName();  // Get logged-in user's email
        model.addAttribute("expenses", expenseService.getAllExpensesForUser(email));
        model.addAttribute("categories", expenseService.getAllCategories());
        return "expenses/list";  // Returns expenses/list.html template
    }

    @GetMapping("/new")
    // Handles GET requests to "/expenses/new" - show create expense form
    public String showNewExpenseForm(Model model) {
        model.addAttribute("expenseDto", new ExpenseDto());  // Empty form backing object
        model.addAttribute("categories", expenseService.getAllCategories());  // For dropdown
        return "expenses/form";
    }

    @PostMapping
    // Handles POST requests to "/expenses" - save new expense
    public String saveExpense(@Valid @ModelAttribute("expenseDto") ExpenseDto expenseDto,
                              // @Valid triggers validation, @ModelAttribute binds form data
                              BindingResult result,  // Captures validation errors
                              Authentication authentication,
                              Model model) {
        if (result.hasErrors()) {
            // Validation failed (e.g., amount negative, title missing)
            model.addAttribute("categories", expenseService.getAllCategories());
            return "expenses/form";  // Return to form with error messages
        }

        expenseService.saveExpense(authentication.getName(), expenseDto);
        return "redirect:/expenses?success";  // Redirect to list with success message
    }

    @GetMapping("/edit/{id}")
    // Handles GET requests to "/expenses/edit/{id}" - show edit form
    public String showEditExpenseForm(@PathVariable Long id,  // Extracts ID from URL
                                      Authentication authentication,
                                      Model model) {
        // Fetch expense by ID, verifying it belongs to the logged-in user
        model.addAttribute("expenseDto", expenseService.getExpenseById(id, authentication.getName()));
        model.addAttribute("categories", expenseService.getAllCategories());
        return "expenses/edit-form";
    }

    @PostMapping("/update/{id}")
    // Handles POST requests to "/expenses/update/{id}" - update existing expense
    public String updateExpense(@PathVariable Long id,
                                @Valid @ModelAttribute("expenseDto") ExpenseDto expenseDto,
                                BindingResult result,
                                Authentication authentication,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", expenseService.getAllCategories());
            return "expenses/edit-form";  // Return to edit form with errors
        }

        expenseService.updateExpense(id, authentication.getName(), expenseDto);
        return "redirect:/expenses?updated";  // Redirect with update success message
    }

    @GetMapping("/delete/{id}")
    // Handles GET requests to "/expenses/delete/{id}" - delete expense
    public String deleteExpense(@PathVariable Long id, Authentication authentication) {
        expenseService.deleteExpense(id, authentication.getName());  // Verify ownership before delete
        return "redirect:/expenses?deleted";  // Redirect with delete success message
    }

    @GetMapping("/search")
    // Handles GET requests to "/expenses/search" - multi-criteria search
    public String searchExpenses(@RequestParam(required = false) Long categoryId,
                                 // Optional parameter - filter by category
                                 @RequestParam(required = false) LocalDate expenseDate,
                                 // Optional parameter - filter by exact date
                                 @RequestParam(required = false) LocalDate startDate,
                                 // Optional parameter - date range start
                                 @RequestParam(required = false) LocalDate endDate,
                                 // Optional parameter - date range end
                                 Authentication authentication,
                                 Model model) {

        String email = authentication.getName();
        List<Expense> expenses;

        // Determine which search method to use based on provided parameters
        if (categoryId != null) {
            expenses = expenseService.searchByCategory(email, categoryId);
        } else if (expenseDate != null) {
            expenses = expenseService.searchByDate(email, expenseDate);
        } else if (startDate != null && endDate != null) {
            expenses = expenseService.searchByDateRange(email, startDate, endDate);
        } else {
            expenses = expenseService.getAllExpensesForUser(email);  // Fallback to all
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("categories", expenseService.getAllCategories());

        return "expenses/list";
    }
}
