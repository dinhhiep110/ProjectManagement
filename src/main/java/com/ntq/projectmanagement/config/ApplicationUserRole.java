package com.ntq.projectmanagement.config;



import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ntq.projectmanagement.config.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    //cac roles voi cac quyen like admin(permissions,permissions)
    PROJECTMANAGER(Sets.newHashSet(PROJECTMANAGER_READ,PROJECTMANAGER_WRITE)),
    EMPLOYEEMANAGER(Sets.newHashSet(EMPLOYEEMANAGER_READ,EMPLOYEEMANAGER_WRITE)),

    ADMIN(Sets.newHashSet(EMPLOYEEMANAGER_WRITE,EMPLOYEEMANAGER_READ,PROJECTMANAGER_WRITE,PROJECTMANAGER_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> applicationUserPermissions) {
        this.permissions = applicationUserPermissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public @NotNull Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
