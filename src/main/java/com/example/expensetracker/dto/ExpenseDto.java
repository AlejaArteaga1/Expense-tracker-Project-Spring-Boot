package com.example.expensetracker.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
// Data Transfer Object for transferring expense data between controller and service layers
public class ExpenseDto {

    private Long id;
    // Optional - present when editing existing expense, null for new expenses

    @NotBlank(message = "Title is required.")
    // Ensures title is not null, not empty, and not just whitespace
    private String title;

    private String description;
    // Optional field - no validation required

    @NotNull(message = "Amount is required.")
    // Ensures amount is not null
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    // Ensures amount is at least 0.01 (positive currency value)
    private BigDecimal amount;

    @NotNull(message = "Expense date is required.")
    // Ensures expense date is not null
    private LocalDate expenseDate;

    @NotNull(message = "Category is required.")
    // Ensures a category is selected (cannot be null)
    private Long categoryId;
    // Stores the category ID reference, not the full Category object
}
