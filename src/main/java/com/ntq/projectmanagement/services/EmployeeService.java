package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.dto.EmployeeDto;
import com.ntq.projectmanagement.entities.Employee;
import com.ntq.projectmanagement.entities.Project;
import com.ntq.projectmanagement.settings.EmployeeFilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    long countAllEmployee();

    long countActiveEmployee(long id);

    List<Employee> getAllEmployee();

    List<EmployeeDto> getAllEmployees();

    List<Employee> getActiveEmployee();

    List<Employee> getAvailableEmployeeForProject(Project project);

    void addUpdateEmployee(Employee employee);

    Page<Employee> getEmployeePaginated(int pageNumber, EmployeeFilterRequest filterRequest);

    Employee getEmployeeById(long id);

    EmployeeDto getEmployeesById(long id);
}
