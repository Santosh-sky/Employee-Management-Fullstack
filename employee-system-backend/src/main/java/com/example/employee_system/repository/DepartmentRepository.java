package com.example.employee_system.repository;

import com.example.employee_system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentName(String departmentName);

}