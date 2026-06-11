package com.example.employee_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String name;
    private String departmentName;
    private Double salary;
}