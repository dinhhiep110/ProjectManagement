package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.entities.Projectemployee;
import com.ntq.projectmanagement.repository.ProjectemployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectemployeeServiceImpl implements ProjectemployeeService{

    @Autowired
    ProjectemployeeRepository projectemployeeRepository;

    @Override
    public List<Projectemployee> getAllEmployeeByProjectId(long id) {
        return projectemployeeRepository.getAllByProjectIdAndIsRejectedFalse(id);
    }

    @Override
    public List<Projectemployee> getAllProjectByEmployeeId(long id) {
        return projectemployeeRepository.getAllByEmployeeId(id);
    }

    @Override
    public void addUpdateProjectEmployee(Projectemployee projectemployee) {
        projectemployeeRepository.saveAndFlush(projectemployee);
    }
}
