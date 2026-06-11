package com.example.employee_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

   /* private String department;*/

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Positive(message = "Salary must be positive")
    private Double salary;
}