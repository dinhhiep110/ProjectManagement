package com.ntq.projectmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private Boolean gender;
    private String email;
    private String address;
    private RoleDto role;
    private LocalDate dateJoinCompany;
    private Boolean isDelete;
    private StatusDto status;
    private Set<LanguageDto> languages;
    private LocalDateTime updateAt;
}
