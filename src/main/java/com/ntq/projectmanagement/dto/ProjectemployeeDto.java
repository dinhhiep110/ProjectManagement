package com.ntq.projectmanagement.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectemployeeDto implements Serializable {
    private  ProjectemployeeIdDto id;
    private  ProjectDto project;
    private  EmployeeDto employee;
    private  LocalDate dateJoin;
    private  LocalDate dateOut;
    private  Boolean isRejected;
}
