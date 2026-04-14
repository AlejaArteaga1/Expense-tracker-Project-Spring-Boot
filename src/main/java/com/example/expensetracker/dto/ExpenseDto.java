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
public class ExpenseDto {

    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    private String description;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    private BigDecimal amount;

    @NotNull(message = "Expense date is required.")
    private LocalDate expenseDate;

    @NotNull(message = "Category is required.")
    private Long categoryId;
}
