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
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String listExpenses(Authentication authentication, Model model) {
        String email = authentication.getName();
        model.addAttribute("expenses", expenseService.getAllExpensesForUser(email));
        model.addAttribute("categories", expenseService.getAllCategories());
        return "expenses/list";
    }

    @GetMapping("/new")
    public String showNewExpenseForm(Model model) {
        model.addAttribute("expenseDto", new ExpenseDto());
        model.addAttribute("categories", expenseService.getAllCategories());
        return "expenses/form";
    }

    @PostMapping
    public String saveExpense(@Valid @ModelAttribute("expenseDto") ExpenseDto expenseDto, BindingResult result, Authentication authentication, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", expenseService.getAllCategories());
            return "expenses/form";
        }

        expenseService.saveExpense(authentication.getName(), expenseDto);
        return "redirect:/expenses?success";
    }

    @GetMapping("/edit/{id}")
    public String showEditExpenseForm(@PathVariable Long id, Authentication authentication, Model model) {
        model.addAttribute("expenseDto", expenseService.getExpenseById(id, authentication.getName()));
        model.addAttribute("categories", expenseService.getAllCategories());
        return "expenses/edit-form";
    }

    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable Long id, @Valid @ModelAttribute("expenseDto") ExpenseDto expenseDto, BindingResult result, Authentication authentication, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", expenseService.getAllCategories());
            return "expenses/edit-form";
        }

        expenseService.updateExpense(id, authentication.getName(), expenseDto);
        return "redirect:/expenses?updated";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, Authentication authentication) {
        expenseService.deleteExpense(id, authentication.getName());
        return "redirect:/expenses?deleted";
    }

    @GetMapping("/search")
    public String searchExpenses(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) LocalDate expenseDate, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate, Authentication authentication, Model model) {

        String email = authentication.getName();
        List<Expense> expenses;

        if (categoryId != null) {
            expenses = expenseService.searchByCategory(email, categoryId);
        } else if (expenseDate != null) {
            expenses = expenseService.searchByDate(email, expenseDate);
        } else if (startDate != null && endDate != null) {
            expenses = expenseService.searchByDateRange(email, startDate, endDate);
        } else {
            expenses = expenseService.getAllExpensesForUser(email);
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("categories", expenseService.getAllCategories());

        return "expenses/list";
    }
}
