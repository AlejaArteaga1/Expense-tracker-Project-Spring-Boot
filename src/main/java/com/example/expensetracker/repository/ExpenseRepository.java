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
    // Finds all expenses for a specific user, ordered by expense date (newest first)

    List<Expense> findByUserAndCategoryIdOrderByExpenseDateDesc(User user, Long categoryId);
    // Finds expenses by user AND category ID, ordered by date descending
    // Used for category filtering/search

    List<Expense> findByUserAndExpenseDateOrderByExpenseDateDesc(User user, LocalDate expenseDate);
    // Finds expenses by user AND exact date, ordered by date descending
    // Used for date-specific searches

    List<Expense> findByUserAndExpenseDateBetweenOrderByExpenseDateDesc(User user, LocalDate startDate, LocalDate endDate);
    // Finds expenses by user within a date range (inclusive), ordered by date descending
    // Used for date range searches

    Optional<Expense> findByIdAndUser(Long id, User user);
    // Finds an expense by BOTH ID and User - ensures user can only access their own expenses
    // Returns Optional to handle case where expense doesn't exist or belongs to different user

    long countByUser(User user);
    // Returns the total number of expenses for a specific user
    // Used for dashboard statistics

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.user = :user
            AND YEAR(e.expenseDate) = :year
            AND MONTH(e.expenseDate) = :month
            """)
    BigDecimal getMonthlyTotal(User user, int year, int month);
    // Custom JPQL query - calculates total expenses for a specific user in a given month
    // COALESCE(..., 0) returns 0 if no expenses found (prevents null)
    // Used for dashboard monthly total display

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            """)
    BigDecimal getSystemTotalAmount();
    // Custom JPQL query - sums ALL expense amounts across every user in the system
    // Used for admin dashboard to show total money tracked in the system
}