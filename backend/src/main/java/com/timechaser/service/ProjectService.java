package com.timechaser.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.ProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.exception.CreateException;
import com.timechaser.exception.NotFoundException;
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
    
    @Transactional
	public ProjectDto update(ProjectDto projectDto, Long id) {
		logger.info("Updating project details with id {}", id);
		
		Project existingProject = projectRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Project not found with ID: " + id));
		
		existingProject.setName(projectDto.getName());
		existingProject = projectRepository.save(existingProject);
		
		
		return ProjectMapper.toDto(existingProject);
	}

    
	public List<ProjectDto> findAll() {
		logger.info("Getting all projects");
		
		List<Project> projects = projectRepository.findAll();
        
        return projects.stream()
        		.map(ProjectMapper::toDto)
        		.collect(Collectors.toList());
	}
}
