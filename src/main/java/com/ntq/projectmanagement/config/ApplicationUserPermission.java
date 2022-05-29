package com.ntq.projectmanagement.config;

public enum ApplicationUserPermission {
    EMPLOYEEMANAGER_READ("empployee:read"),
    EMPLOYEEMANAGER_WRITE("empployee:write"),
    PROJECTMANAGER_READ("project:read"),
    PROJECTMANAGER_WRITE("project:write");
    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
