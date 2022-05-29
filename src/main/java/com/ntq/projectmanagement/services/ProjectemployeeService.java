package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.entities.Projectemployee;

import java.util.List;

public interface ProjectemployeeService {
    List<Projectemployee> getAllEmployeeByProjectId(long id);

    List<Projectemployee> getAllProjectByEmployeeId(long id);

    void addUpdateProjectEmployee(Projectemployee projectemployee);
}
