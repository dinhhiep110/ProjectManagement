package com.ntq.projectmanagement.controller;

import com.ntq.projectmanagement.dto.EmployeeDto;
import com.ntq.projectmanagement.entities.Employee;
import com.ntq.projectmanagement.entities.Projectemployee;
import com.ntq.projectmanagement.entities.Role;
import com.ntq.projectmanagement.entities.Status;
import com.ntq.projectmanagement.services.*;
import com.ntq.projectmanagement.settings.EmployeeFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping({"/employee","/e","/em"})
public class EmployeeController {
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

    @GetMapping("/list")
    public String getAllEmployee(ModelMap model,@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @ModelAttribute(name = "filter") EmployeeFilterRequest filterRequest){
        if(filterRequest.getPageSize() == 0){
            filterRequest.setPageSize(3);
        }
        if(filterRequest.getKeyword() == null){
            filterRequest.setKeyword("");
        }
        Page<Employee> paginated = employeeService.getEmployeePaginated(pageNumber,filterRequest);
        if(pageNumber < 0 || pageNumber > paginated.getTotalPages()){
            pageNumber = 1;
            return "redirect:list";
        }
        List<Employee> allEmployee = paginated.getContent();
        if(allEmployee.size() == 0 || allEmployee == null){
            model.addAttribute("allEmployee",null);
        }
        else {
            model.addAttribute("allEmployee",allEmployee);
        }
        model.addAttribute("filter",new EmployeeFilterRequest());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());
        model.addAttribute("employee",new Employee());
        model.addAttribute("filterRequest",filterRequest);
        model.addAttribute("keyword",filterRequest.getKeyword());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("language",languageService.getAllLanguages());
        return "employee";
    }


    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee, @ModelAttribute Role r){
        employee.setDateJoinCompany(LocalDate.now());
        employee.setIsDelete(false);
        Role role = roleService.getRoleById(r.getId());
        employee.setRole(role);
        System.out.println(role.getRoleName());
        Status status = statusService.getStatusById(3);
        employee.setStatus(status);
        employee.setUpdateAt(LocalDateTime.now());
        employeeService.addUpdateEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/detail/{id}")
    public String getDetailEmployee(ModelMap model,@PathVariable(name = "id") long id){
        Employee employee = employeeService.getEmployeeById(id);
        List<Projectemployee> projectemployees = projectemployeeService.getAllProjectByEmployeeId(id);
        if(projectemployees.equals(null) || projectemployees.size() == 0){
            projectemployees = null;
        }
        model.addAttribute("projectemployees",projectemployees);
        model.addAttribute("employee",employee);
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("lang",languageService.getAllLanguages());
        return "employeedetail";
    }

    @PostMapping("/edit")
    public String editEmployee(@ModelAttribute Employee employee){
        Employee employeeById = employeeService.getEmployeeById(employee.getId());
        for (Projectemployee projectemployee : employeeById.getProjectemployees()) {
            if(!projectemployee.getIsRejected()){//dang join du an k the edit
                return "redirect:/employee/list";
            }
        }
        employee.setUpdateAt(LocalDateTime.now());
        employeeService.addUpdateEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(name = "id") long id){
        Employee employee = employeeService.getEmployeeById(id);
        for (Projectemployee projectemployee : employee.getProjectemployees()) {
            projectemployee.setIsRejected(true);
        }
        employee.setIsDelete(true);
        Status status = statusService.getStatusById(4L);
        employee.setStatus(status);
        employeeService.addUpdateEmployee(employee);
        return "redirect:/employee/list";
    }

}
