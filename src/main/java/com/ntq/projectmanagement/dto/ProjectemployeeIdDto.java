package com.ntq.projectmanagement.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectemployeeIdDto implements Serializable {
    private  Long projectId;
    private  Long employeeId;
}
