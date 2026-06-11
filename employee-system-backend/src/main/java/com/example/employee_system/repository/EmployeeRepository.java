package com.example.employee_system.repository;

import com.example.employee_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment_DepartmentName(String departmentName);
}
