package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
