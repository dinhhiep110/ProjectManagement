package com.ntq.projectmanagement.repository;

import com.ntq.projectmanagement.entities.Employee;
import com.ntq.projectmanagement.entities.Language;
import com.ntq.projectmanagement.settings.EmployeeFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    long countEmployeeByStatusId(Long status_id);

    List<Employee>getAllByStatusId(Long status_id);

    Page<Employee>getAllByNameLikeIgnoreCaseAndStatusIdAndIsDeleteFalse(Pageable pageable,String keyword,long status);

   @Query(value = "SELECT distinct employee FROM Employee employee left join employee.languages el\n" +
   " where (coalesce(:#{#filter.keyword}, '') = '' or employee.name LIKE CONCAT('%',:#{#filter.keyword},'%'))\n " +
           " and (coalesce(:#{#filter.status}, 0) = 0 or employee.status.id = :#{#filter.status}) \n " +
           " and (coalesce(:#{#filter.role}, 0) = 0 or employee.role.id= :#{#filter.role})\n " +
           " and (coalesce(:#{#filter.language}, 0) = 0 or  el.id = :#{#filter.language}) and employee.isDelete = false \n")
    Page<Employee>filterEmployeeWithKeyWord(@Param("filter")EmployeeFilterRequest filterRequest,Pageable pageable);
}