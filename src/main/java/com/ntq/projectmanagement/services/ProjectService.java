package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.dto.ProjectDto;
import com.ntq.projectmanagement.entities.Employee;
import com.ntq.projectmanagement.entities.Project;
import com.ntq.projectmanagement.settings.ProjectFilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {
    long countAllProject();

    long countAllProjectOpen();

    List<Project> getAllProject();

    List<ProjectDto> getAllProjects();

    Project getProjectById(long id);

    Page<Project> getProjectPaginated(int pageNumber, ProjectFilterRequest projectFilterRequest);

    void addAndUpdateProject(Project project);
}
