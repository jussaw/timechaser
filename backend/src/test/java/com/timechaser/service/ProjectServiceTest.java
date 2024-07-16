package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timechaser.dto.CreateProjectResponse;
import com.timechaser.entity.Project;
import com.timechaser.exception.CreateException;
import com.timechaser.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private String projectName;

    @BeforeEach
    void setUp() {
        projectName = "Test Project";

        project = new Project();
        project.setName(projectName);
    }

    @Test
    void ProjectService_Create_Success() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        CreateProjectResponse response = projectService.create(projectName);

        assertNotNull(response);
        assertEquals(project.getId(), response.getId());
        assertEquals(project.getName(), response.getName());
    }

    @Test
    void ProjectService_Create_Failure() {
        when(projectRepository.save(any(Project.class))).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> projectService.create(projectName))
            .isInstanceOf(CreateException.class);
    }
    
}
