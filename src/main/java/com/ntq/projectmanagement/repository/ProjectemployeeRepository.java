package com.ntq.projectmanagement.repository;

import com.ntq.projectmanagement.entities.Projectemployee;
import com.ntq.projectmanagement.entities.ProjectemployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectemployeeRepository extends JpaRepository<Projectemployee, ProjectemployeeId> {
    int countAllByEmployeeIdAndIsRejectedFalse(long employee_id);

    List<Projectemployee>getAllByProjectIdAndIsRejectedFalse(long id);

    List<Projectemployee>getAllByEmployeeId(long id);
}