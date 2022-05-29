package com.ntq.projectmanagement.restcontroller;

import com.ntq.projectmanagement.dto.EmployeeDto;
import com.ntq.projectmanagement.dto.ProjectDto;
import com.ntq.projectmanagement.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    @Autowired
    ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<?> getAllProjects(){
        try{
            List<ProjectDto> allProjects = projectService.getAllProjects();
            if(allProjects.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (Objects.isNull(allProjects)) {
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(allProjects,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
