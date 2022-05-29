package com.ntq.projectmanagement.controller;

import com.ntq.projectmanagement.entities.*;
import com.ntq.projectmanagement.services.*;
import com.ntq.projectmanagement.settings.ProjectFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequestMapping({"/project","/p"})
@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    SettingService settingService;

    @Autowired
    LanguageService languageService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    StatusService statusService;

    @Autowired
    ProjectemployeeService projectemployeeService;

    @GetMapping("/list")
    public String getAllProject(ModelMap model,@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @ModelAttribute ProjectFilterRequest filterRequest){
        if(filterRequest.getPageSize() == 0){
            filterRequest.setPageSize(3);
        }
        if(filterRequest.getKeyword() == null){
            filterRequest.setKeyword("");
        }
        Page<Project> projectPage = projectService.getProjectPaginated(pageNumber,filterRequest);
        if(pageNumber < 0 || pageNumber > projectPage.getTotalPages()){
            pageNumber = 1;
            return "redirect:list";
        }
        List<Project> projects = projectPage.getContent();
        if(projects.size() == 0 || projects == null){
            model.addAttribute("projects",null);
        }
        else {
            model.addAttribute("projects",projects);
        }
        model.addAttribute("project",new Project());
        model.addAttribute("language",languageService.getAllLanguages());
        model.addAttribute("filter",new ProjectFilterRequest());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("filterRequest",filterRequest);
        model.addAttribute("totalPages", projectPage.getTotalPages());
        model.addAttribute("totalItems", projectPage.getTotalElements());
        model.addAttribute("keyword",filterRequest.getKeyword());
        return "project";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute Project project){
        System.out.println(project);
        System.out.println(project.getLanguages());
        project.setStatus(statusService.getStatusById(1));
        projectService.addAndUpdateProject(project);
        project.setUpdateAt(LocalDateTime.now());
        return "redirect:/project/list";
    }

    @GetMapping("/detail/{id}")
    public String getEmployeeProject(@PathVariable(name = "id") long id, Model model){
        Project project = projectService.getProjectById(id);
        List<Employee> employee = employeeService.getAvailableEmployeeForProject(project);
        if(project.getProjectemployees() == null || project.getProjectemployees().size() == 0){
            model.addAttribute("projectEmployee",null);
        }
        else{
            model.addAttribute("projectEmployee",projectemployeeService.getAllEmployeeByProjectId(project.getId()));
        }
        model.addAttribute("projectEm",new Projectemployee());
        model.addAttribute("project",project);
        model.addAttribute("employee",employee);
        return "projectdetail";
    }

    @PostMapping("/edit")
    public String addEmployeeProject(@ModelAttribute Projectemployee projectemployee){
        projectemployee.setDateJoin(LocalDate.now());
        ProjectemployeeId projectemployeeId = new ProjectemployeeId();
        projectemployeeId.setEmployeeId(projectemployee.getEmployee().getId());
        projectemployeeId.setProjectId(projectemployee.getProject().getId());
        projectemployee.setId(projectemployeeId);
        projectemployee.setIsRejected(false);
        projectemployee.getProject().setUpdateAt(LocalDateTime.now());
        projectemployee.getEmployee().setUpdateAt(LocalDateTime.now());
        projectemployeeService.addUpdateProjectEmployee(projectemployee);
        return "redirect:/project/detail/" + projectemployee.getProject().getId();
    }

    @GetMapping("/close/{id}")
    public String closeProject(@PathVariable(name = "id")long id){
        Project project = projectService.getProjectById(id);
        Status status = statusService.getStatusById(2L);
        project.setStatus(status);
        Set<Projectemployee> projectemployees = project.getProjectemployees();
        for (Projectemployee projectemployee : projectemployees) {
            projectemployee.setDateOut(LocalDate.from(LocalDateTime.now()));
            projectemployee.setIsRejected(true);
            projectemployeeService.addUpdateProjectEmployee(projectemployee);
        }
        projectService.addAndUpdateProject(project);
        return "redirect:/project/list";
    }

}
