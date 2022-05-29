package com.ntq.projectmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatusDto implements Serializable {
    private Long id;
    private String name;
    @JsonIgnore
    private Set<EmployeeDto> employees;
}
