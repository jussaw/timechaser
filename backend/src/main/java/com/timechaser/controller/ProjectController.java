package com.timechaser.controller;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.timechaser.dto.ProjectDto;
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
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
    	logger.info("Received request to update project with id: {}");
    	
    	projectDto = projectService.update(projectDto, id);
    	
    	return ResponseEntity.status(HttpStatus.OK).body(projectDto);
    }
}
