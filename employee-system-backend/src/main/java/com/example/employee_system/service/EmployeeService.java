package com.example.employee_system.service;

import com.example.employee_system.entity.Employee;

import org.springframework.data.domain.Page;

import java.util.List;
import com.example.employee_system.dto.EmployeeRequest;
import com.example.employee_system.dto.EmployeeResponse;

public interface EmployeeService {

    //Employee saveEmployee(Employee employee);
    EmployeeResponse saveEmployee(EmployeeRequest request);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    void deleteEmployee(Long id);

    //Employee updateEmployee(Long id, Employee employee);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    Page<Employee> getEmployeesWithPagination(int page,int size);

    List<Employee> sortEmployeesBySalary();

    List<Employee> searchByDepartment(String department);

    List<Employee> sortEmployees(String field, String direction);

    Page<Employee> getEmployeesWithPaginationAndSorting(
            int page,
            int size,
            String field,
            String direction
    );
}