package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(long id);
}
