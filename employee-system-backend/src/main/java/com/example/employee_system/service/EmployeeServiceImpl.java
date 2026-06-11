package com.example.employee_system.service;
import com.example.employee_system.dto.EmployeeRequest;
import com.example.employee_system.dto.EmployeeResponse;
import com.example.employee_system.entity.Department;
import com.example.employee_system.exception.ResourceNotFoundException;
import com.example.employee_system.repository.DepartmentRepository;
import org.springframework.data.domain.Sort;
import com.example.employee_system.entity.Employee;
import com.example.employee_system.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


//import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private DepartmentRepository departmentRepository;

   /* @Override
    public Employee saveEmployee(Employee employee) {
        return repo.save(employee);
    }*/

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + request.getDepartmentId()
                        )
                );

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setDepartment(department);
        employee.setSalary(request.getSalary());

        Employee savedEmployee = repo.save(employee);

        EmployeeResponse response = new EmployeeResponse();

        response.setId(savedEmployee.getId());
        response.setName(savedEmployee.getName());
        response.setDepartmentName(savedEmployee.getDepartment().getDepartmentName());
        response.setSalary(savedEmployee.getSalary());

        return response;
    }
    /*@Override
    public List<Employee> getAllEmployees() {

        List<Employee> list = new ArrayList<>();

        repo.findAll().forEach(list::add);

        return list;
    }*/

    @Override
    public List<Employee>getAllEmployees(){
        return repo.findAll();
    }
    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee existingEmployee = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + request.getDepartmentId()
                        )
                );

        existingEmployee.setName(request.getName());
        existingEmployee.setDepartment(department);
        existingEmployee.setSalary(request.getSalary());

        Employee updatedEmployee = repo.save(existingEmployee);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(updatedEmployee.getId());
        response.setName(updatedEmployee.getName());
        response.setDepartmentName(updatedEmployee.getDepartment().getDepartmentName());
        response.setSalary(updatedEmployee.getSalary());

        return response;
    }

    /*@Override
    public Employee getEmployeeById(Long id) {
        return repo.findById(id).orElse(null);
    }
*/
    @Override
    public Employee getEmployeeById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
    }

   /* @Override
    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }
  */

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        repo.delete(employee);
    }

    @Override
    public Page<Employee> getEmployeesWithPagination(
            int page,
            int size) {

        return repo.findAll(
                PageRequest.of(page, size)
        );
    }

    @Override
    public List<Employee> sortEmployeesBySalary() {
        return repo.findAll(Sort.by("salary"));
        //Sort.by(Sort.Direction.DESC, "salary")
    }

    @Override
    public List<Employee> searchByDepartment(
            String department) {

        return repo.findByDepartment_DepartmentName(department);
    }

    @Override
    public List<Employee> sortEmployees(String field, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        return repo.findAll(sort);
    }

    @Override
    public Page<Employee> getEmployeesWithPaginationAndSorting(
            int page,
            int size,
            String field,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return repo.findAll(pageRequest);
    }
}