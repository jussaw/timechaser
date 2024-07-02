package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timechaser.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
