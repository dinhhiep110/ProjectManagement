package com.ntq.projectmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto implements Serializable {
    private Long id;
    private String roleName;
    @JsonIgnore
    private Set<EmployeeDto> employees;
}
