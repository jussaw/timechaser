package com.timechaser.controller;

import java.util.Optional;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.timechaser.dto.ProjectDto;
import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.Role;
import com.timechaser.exception.NotFoundException;
import com.timechaser.mapper.ProjectMapper;
import com.timechaser.mapper.RoleMapper;
import com.timechaser.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    

    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).ADMIN)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {

        logger.info("Received request to create project with name {}", projectDto.getName());

        projectDto = projectService.create(projectDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectDto);
    }
    
    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).ADMIN)")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDto projectDto) {
    	logger.info("Received request to update project with id: {}");
    	
    	projectDto = projectService.update(projectDto, id);
    	
    	return ResponseEntity.status(HttpStatus.OK).build();
    }
    

    @GetMapping("/{id}")
	public ResponseEntity<ProjectDto> findProjectById(@PathVariable Long id){
		logger.info("Received request to get project by ID: {}", id);
		
		Optional<Project> projectOptional = projectService.findById(id);
		
		ProjectDto response = projectOptional
		        .map(ProjectMapper::toDto)
		        .orElseThrow(() -> new NotFoundException("Project not found with ID: " + id));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
    
    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).ADMIN)")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
		logger.info("Received request to delete project with ID {}", id);
		
		projectService.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}	

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> findAllProjects() {
    	logger.info("Received request to get all projects");
    	
    	List<ProjectDto> projectDtos = projectService.findAll();
    	
    	return ResponseEntity.status(HttpStatus.OK).body(projectDtos);
    }
}
