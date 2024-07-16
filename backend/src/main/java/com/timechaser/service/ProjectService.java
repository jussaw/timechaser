package com.timechaser.service;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.CreateProjectResponse;
import com.timechaser.entity.Project;
import com.timechaser.exception.CreateException;
import com.timechaser.repository.ProjectRepository;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public CreateProjectResponse create(String name) {
        logger.info("Creating project with name {}", name);

        try {
            Project project = new Project(name);
            project = projectRepository.save(project);

            return new CreateProjectResponse(project);
        } catch (Exception e) {
            logger.error("Error occured while creating project with name {}", name);
            throw new CreateException("Failed to create project", e);
        }
    }

    public Optional<Project> findByName(String name) {
        logger.info("Finding project with name: {}", name);

        Optional<Project> project = projectRepository.findByName(name);

        return project;
    }
}
