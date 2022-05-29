package com.ntq.projectmanagement.repository;

import com.ntq.projectmanagement.entities.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {

}