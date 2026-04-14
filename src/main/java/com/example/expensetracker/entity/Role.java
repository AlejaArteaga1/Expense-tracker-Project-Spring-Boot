package com.example.expensetracker.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key

    @Column(nullable = false, unique = true, length = 50)
    private String name;  // Role name like "ROLE_USER" or "ROLE_ADMIN"

    public Role(String name) {
        this.name = name;
    }
}