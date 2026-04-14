package com.example.expensetracker.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key, auto-incremented

    @Column(nullable = false, unique = true, length = 50)
    private String name;  // Category name - required and must be unique

    @Column(length = 255)
    private String description;  // Optional category description

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}