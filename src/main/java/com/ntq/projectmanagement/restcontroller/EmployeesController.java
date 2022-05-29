package com.ntq.projectmanagement.restcontroller;

import com.ntq.projectmanagement.dto.EmployeeDto;
import com.ntq.projectmanagement.dto.Respond;
import com.ntq.projectmanagement.entities.Employee;
import com.ntq.projectmanagement.entities.Status;
import com.ntq.projectmanagement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleService roleService;

    @Autowired
    LanguageService languageService;

    @Autowired
    StatusService statusService;

    @Autowired
    SettingService settingService;

    @Autowired
    ProjectemployeeService projectemployeeService;

    @GetMapping("")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        try{
            List<EmployeeDto> employees = employeeService.getAllEmployees();
            if(employees.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (Objects.isNull(employees)) {
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(employees,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long id){
        try{
            EmployeeDto employee = employeeService.getEmployeesById(id);
            if(employee == null || employee.getId() == null){
                return new ResponseEntity<>(new Respond<>("Employee is not found",null),HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(new Respond<>("Employee is found",employee),HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(new Respond<>("Employee is not found",null),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<String> addEmployees(@Validated @RequestBody Employee employee){
        try{
            if(employee == null){
                return new ResponseEntity<>("Add UnSuccessfully",HttpStatus.BAD_REQUEST);
            }
            employee.setDateJoinCompany(LocalDate.now());
            employee.setIsDelete(false);
            Status status = statusService.getStatusById(3);
            employee.setStatus(status);
            employee.setUpdateAt(LocalDateTime.now());
            employeeService.addUpdateEmployee(employee);
            return new ResponseEntity<>("Add Successfully",HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>("Add UnSuccessfully",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployees(@PathVariable long id,@Validated @RequestBody Employee employee){
        try{
            Employee oldEmployees = employeeService.getEmployeeById(id);
            if(employee == null){
                return new ResponseEntity<>("Employee is not existed",HttpStatus.BAD_REQUEST);
            }
            oldEmployees.setName(employee.getName() == null ? oldEmployees.getName() : employee.getName());
            oldEmployees.setAge(employee.getAge() == 0 ? oldEmployees.getAge() : employee.getAge());
            oldEmployees.setAddress(employee.getAddress() == null ? oldEmployees.getAddress() : employee.getAddress());
            oldEmployees.setDateJoinCompany(employee.getDateJoinCompany() == null ? oldEmployees.getDateJoinCompany() : employee.getDateJoinCompany());
            oldEmployees.setEmail(employee.getEmail() == null ? oldEmployees.getEmail() : employee.getEmail());
            oldEmployees.setGender(employee.getGender() == null ? oldEmployees.getGender() : employee.getGender());
            oldEmployees.setStatus(employee.getStatus() == null ? oldEmployees.getStatus() : employee.getStatus());
            oldEmployees.setUpdateAt(LocalDateTime.now());
            oldEmployees.setRole(employee.getRole() == null ? oldEmployees.getRole() : employee.getRole());
            oldEmployees.setLanguages(employee.getLanguages() == null ? oldEmployees.getLanguages() : employee.getLanguages());
            oldEmployees.setIsDelete(employee.getIsDelete() == null ? oldEmployees.getIsDelete() : employee.getIsDelete());
            employeeService.addUpdateEmployee(oldEmployees);
            return new ResponseEntity<>("Update Successfully",HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>("Update UnSuccessfully",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable long id){
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if(employee == null || employee.getId() == null){
                return new ResponseEntity<>("Employee is not existed",HttpStatus.BAD_REQUEST);
            }
            employee.setIsDelete(true);
            Status status = statusService.getStatusById(4L);
            employee.setStatus(status);
            employeeService.addUpdateEmployee(employee);
            return new ResponseEntity<>("Delete Successfully",HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>("Delete UnSuccessfully",HttpStatus.NOT_FOUND);
        }
    }
}
