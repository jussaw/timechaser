package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timechaser.dto.ProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.exception.CreateException;
import com.timechaser.exception.NotFoundException;
import com.timechaser.mapper.ProjectMapper;
import com.timechaser.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectDto projectDto;
    private String projectName;

    @BeforeEach
    void setUp() {
        projectName = "Test Project";

        project = new Project();
        project.setName(projectName);

        projectDto = ProjectMapper.toDto(project);
    }

    @Test
    void ProjectService_Create_Success() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDto response = projectService.create(projectDto);

        assertNotNull(response);
        assertEquals(project.getId(), response.getId());
        assertEquals(project.getName(), response.getName());
    }

    @Test
    void ProjectService_Create_Failure() {
        when(projectRepository.save(any(Project.class))).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> projectService.create(projectDto))
            .isInstanceOf(CreateException.class);
    }
    
    @Test
    void ProjectService_UpdateProject_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        
        ProjectDto result = projectService.update(projectDto, 1L);
        
        assertNotNull(result);
        assertEquals(projectDto.getId(), result.getId());
        assertEquals(projectDto.getName(), result.getName());
    }
    
    @Test
    void ProjectService_UpdateProject_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(NotFoundException.class, () -> {
            projectService.update(projectDto, 1L);
        });
        
        String expectedMessage = "Project not found with ID: 1";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }
    

    @Test
	void ProjectService_findById_Success() {
		when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
		
		Project result = projectService.findById(1L).get();
		
		assertNotNull(result);
        assertEquals(projectDto.getId(), result.getId());
        assertEquals(projectDto.getName(), result.getName());
	}
    
	@Test
	void ProjectService_findById_NotExist() {
		when(projectService.findById(2L)).thenThrow(new NotFoundException("Project not found by id"));
		
		assertThatThrownBy(() ->  projectService.findById(2L))
		.isInstanceOf(NotFoundException.class);
	}
	
	@Test
	void ProjectService_Delete_Success() {
		projectService.deleteById(1L);
		
		verify(projectRepository, times(1)).deleteById(1L);
	}
	
    @Test
    void ProjectService_FindAll_Success() {
        List<Project> projects = Arrays.asList(project);
        when(projectRepository.findAll()).thenReturn(projects);
        
        List<ProjectDto> result = projectService.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(projectDto.getId(), result.get(0).getId());
        assertEquals(projectDto.getName(), result.get(0).getName());
    }
}
