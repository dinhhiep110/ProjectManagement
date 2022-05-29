package com.ntq.projectmanagement.settings;

import lombok.Data;

@Data
public class EmployeeFilterRequest {
    private int pageSize;
    private String keyword;
    private long role;
    private long status;
    private long language;
}
