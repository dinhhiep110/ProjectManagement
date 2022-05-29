package com.ntq.projectmanagement.controller;

import com.ntq.projectmanagement.services.EmployeeService;
import com.ntq.projectmanagement.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    final
    EmployeeService employeeService;

    final
    ProjectService projectService;

    public HomeController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping({"/","/home"})
    public String getInformation(ModelMap model){
        long totalEmployee = employeeService.countAllEmployee();
        long totalProject = projectService.countAllProject();
        long employeeActivePercent = 0,projectOpenPercent = 0;
        if(totalEmployee == 0){
            employeeActivePercent = 0;
        }
        else{
            employeeActivePercent = (employeeService.countActiveEmployee(3L)/totalEmployee) * 100;
        }
        if (totalProject == 0) {
            projectOpenPercent = 0;
        } else{
            projectOpenPercent = (projectService.countAllProjectOpen()/totalProject) * 100;
        }
        model.addAttribute("projectOpenPercent",projectOpenPercent);
        model.addAttribute("employeePercent",employeeActivePercent);
        model.addAttribute("totalEmployee",totalEmployee);
        model.addAttribute("totalProject",totalProject);
        return "index";
    }



}
