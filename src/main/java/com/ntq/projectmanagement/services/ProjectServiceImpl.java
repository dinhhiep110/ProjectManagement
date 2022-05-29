package com.ntq.projectmanagement.services;


import com.ntq.projectmanagement.dto.ProjectDto;
import com.ntq.projectmanagement.entities.Project;
import com.ntq.projectmanagement.repository.ProjectRepository;
import com.ntq.projectmanagement.settings.ProjectFilterRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public long countAllProject() {
        return projectRepository.count();
    }

    @Override
    public long countAllProjectOpen() {
        return projectRepository.countAllByStatusId(1L);
    }

    @Override
    public Page<Project> getProjectPaginated(int pageNumber, ProjectFilterRequest projectFilterRequest) {
        Sort sort = Sort.by("updateAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1,projectFilterRequest.getPageSize(), sort);
        return projectRepository.filterProjectWithKeyWord(projectFilterRequest,pageable);
    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public void addAndUpdateProject(Project project) {
        projectRepository.saveAndFlush(project);
    }

    @Override
    public Project getProjectById(long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.orElseGet(Project::new);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return modelMapper.map(projectRepository.findAll(),new TypeToken<List<ProjectDto>>(){}.getType());
    }
}
