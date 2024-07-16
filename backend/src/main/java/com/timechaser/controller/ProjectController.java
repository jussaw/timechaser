package com.timechaser.controller;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.timechaser.dto.CreateProjectResponse;
import com.timechaser.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole(T(com.timechaser.security.UserRoles).ADMIN)")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CreateProjectResponse> createProject(@Valid @RequestBody String name) {

        logger.info("Received request to create project with name {}", name);

        CreateProjectResponse response = projectService.create(name);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
