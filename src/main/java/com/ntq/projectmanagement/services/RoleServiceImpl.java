package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.entities.Role;
import com.ntq.projectmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElseGet(Role::new);
    }
}
