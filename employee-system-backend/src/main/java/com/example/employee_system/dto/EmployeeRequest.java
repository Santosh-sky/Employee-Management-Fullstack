package com.example.employee_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Department id cannot be empty")
    private Long departmentId;

    @Positive(message = "Salary must be positive")
    private Double salary;
}