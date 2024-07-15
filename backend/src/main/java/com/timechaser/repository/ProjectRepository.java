package com.timechaser.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
}
