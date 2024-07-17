package com.timechaser.service;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.ProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.exception.CreateException;
import com.timechaser.mapper.ProjectMapper;
import com.timechaser.repository.ProjectRepository;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectDto create(ProjectDto projectDto) {
        logger.info("Creating project with name {}", projectDto.getName());

        try {
            Project project = ProjectMapper.toEntity(projectDto);
            project = projectRepository.save(project);

            return ProjectMapper.toDto(project);
        } catch (Exception e) {
            logger.error("Error occured while creating project with name {}", projectDto.getName());
            throw new CreateException("Failed to create project", e);
        }
    }

    public Optional<Project> findByName(String name) {
        logger.info("Finding project with name: {}", name);

        Optional<Project> project = projectRepository.findByName(name);

        return project;
    }
}
