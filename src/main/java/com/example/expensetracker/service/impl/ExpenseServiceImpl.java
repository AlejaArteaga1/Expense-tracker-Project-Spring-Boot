package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserServiceImpl userService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository,
                              UserServiceImpl userService) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public void saveExpense(String userEmail, ExpenseDto expenseDto) {
        User user = userService.findByEmail(userEmail);
        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        Expense expense = new Expense();
        expense.setTitle(expenseDto.getTitle());
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setCategory(category);
        expense.setUser(user);

        expenseRepository.save(expense);
    }

    @Override
    public void updateExpense(Long expenseId, String userEmail, ExpenseDto expenseDto) {
        User user = userService.findByEmail(userEmail);
        Expense expense = expenseRepository.findByIdAndUser(expenseId, user)
                .orElseThrow(() -> new RuntimeException("Expense not found."));

        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        expense.setTitle(expenseDto.getTitle());
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setCategory(category);

        expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long expenseId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Expense expense = expenseRepository.findByIdAndUser(expenseId, user)
                .orElseThrow(() -> new RuntimeException("Expense not found."));
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseDto getExpenseById(Long expenseId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Expense expense = expenseRepository.findByIdAndUser(expenseId, user)
                .orElseThrow(() -> new RuntimeException("Expense not found."));

        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setTitle(expense.getTitle());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDate(expense.getExpenseDate());
        dto.setCategoryId(expense.getCategory().getId());

        return dto;
    }

    @Override
    public List<Expense> getAllExpensesForUser(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return expenseRepository.findByUserOrderByExpenseDateDesc(user);
    }

    @Override
    public List<Expense> searchByCategory(String userEmail, Long categoryId) {
        User user = userService.findByEmail(userEmail);
        return expenseRepository.findByUserAndCategoryIdOrderByExpenseDateDesc(user, categoryId);
    }

    @Override
    public List<Expense> searchByDate(String userEmail, LocalDate date) {
        User user = userService.findByEmail(userEmail);
        return expenseRepository.findByUserAndExpenseDateOrderByExpenseDateDesc(user, date);
    }

    @Override
    public List<Expense> searchByDateRange(String userEmail, LocalDate startDate, LocalDate endDate) {
        User user = userService.findByEmail(userEmail);
        return expenseRepository.findByUserAndExpenseDateBetweenOrderByExpenseDateDesc(user, startDate, endDate);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
}
