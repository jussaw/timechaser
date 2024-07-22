package com.timechaser.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

    public Optional<Project> findByName(String name);
    
    public Optional<Project> findById(long id);
}
