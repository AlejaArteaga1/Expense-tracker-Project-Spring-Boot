package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserOrderByExpenseDateDesc(User user);

    List<Expense> findByUserAndCategoryIdOrderByExpenseDateDesc(User user, Long categoryId);

    List<Expense> findByUserAndExpenseDateOrderByExpenseDateDesc(User user, LocalDate expenseDate);

    List<Expense> findByUserAndExpenseDateBetweenOrderByExpenseDateDesc(User user, LocalDate startDate, LocalDate endDate);

    Optional<Expense> findByIdAndUser(Long id, User user);

    long countByUser(User user);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.user = :user
            AND YEAR(e.expenseDate) = :year
            AND MONTH(e.expenseDate) = :month
            """)
    BigDecimal getMonthlyTotal(User user, int year, int month);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            """)
    BigDecimal getSystemTotalAmount();
}