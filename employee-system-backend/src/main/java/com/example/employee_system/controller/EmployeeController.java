package com.example.employee_system.controller;

import com.example.employee_system.dto.ApiResponse;
import com.example.employee_system.dto.EmployeeRequest;
import com.example.employee_system.dto.EmployeeResponse;
import com.example.employee_system.entity.Employee;
import com.example.employee_system.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    /*@PostMapping
    public Employee saveEmployee(@Valid @RequestBody Employee employee){
        return service.saveEmployee(employee);
    }*/

    /*@PostMapping
    public EmployeeResponse saveEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return service.saveEmployee(request);
    }
*/
    @PostMapping
    public ApiResponse<EmployeeResponse> saveEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = service.saveEmployee(request);

        return new ApiResponse<>(
                true,
                "Employee created successfully",
                response
        );
    }

   /* @GetMapping
    public List<Employee> getAllEmployees(Employee employee){
        return service.getAllEmployees();
    }
*/
   @GetMapping
   public ApiResponse<List<Employee>> getAllEmployees() {

       List<Employee> employees = service.getAllEmployees();

       return new ApiResponse<>(
               true,
               "Employees fetched successfully",
               employees
       );
   }

    /* @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return service.getEmployeeById(id);
    }
    */

    @GetMapping("/{id}")
    public ApiResponse<Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = service.getEmployeeById(id);

        return new ApiResponse<>(
                true,
                "Employee fetched successfully",
                employee
        );
    }

    /*@DeleteMapping ("/{id}")
    public String deleteEmployee(@PathVariable Long id){
         service.deleteEmployee(id);
         return "Employee get Deleted...";
    }*/

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEmployee(@PathVariable Long id) {

        service.deleteEmployee(id);

        return new ApiResponse<>(
                true,
                "Employee deleted successfully",
                "Deleted employee id: " + id
        );
    }

   /* @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        return service.updateEmployee(id, employee);
    }*/

   /* @PutMapping("/{id}")
    public ApiResponse<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        Employee updatedEmployee = service.updateEmployee(id, employee);

        return new ApiResponse<>(
                true,
                "Employee updated successfully",
                updatedEmployee
        );
    }*/

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = service.updateEmployee(id, request);

        return new ApiResponse<>(
                true,
                "Employee updated successfully",
                response
        );
    }

    @GetMapping("/pagination")
    public Page<Employee> getEmployeesWithPagination(

            @RequestParam int page,
            @RequestParam int size) {

        return service.getEmployeesWithPagination(
                page,
                size
        );
    }

    @GetMapping("/sortBySalary")
    public List<Employee> sortEmployeesBySalary() {

        return service.sortEmployeesBySalary();
    }

    @GetMapping("/search/{department}")
    public List<Employee> searchByDepartment(
            @PathVariable String department) {

        return service.searchByDepartment(department);
    }

    @GetMapping("/sort")
    public List<Employee> sortEmployees(
            @RequestParam String field,
            @RequestParam String direction) {

        return service.sortEmployees(field, direction);
    }

    @GetMapping("/page-sort")
    public Page<Employee> getEmployeesWithPaginationAndSorting(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field,
            @RequestParam String direction) {

        return service.getEmployeesWithPaginationAndSorting(
                page,
                size,
                field,
                direction
        );
    }
}