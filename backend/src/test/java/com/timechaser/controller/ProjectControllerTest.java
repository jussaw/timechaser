package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.ProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.User;
import com.timechaser.enums.UserRoles;
import com.timechaser.exception.NotFoundException;
import com.timechaser.mapper.ProjectMapper;
import com.timechaser.service.ProjectService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = { UserRoles.ADMIN })
public class ProjectControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProjectService projectService;

	private Project project;
	private ProjectDto projectDto;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		project = new Project();
		project.setId(1L);
		project.setName("Test Project");

		projectDto = ProjectMapper.toDto(project);
	}

	@Test
    void ProjectController_CreateProject_ReturnCreated() throws Exception {
        when(projectService.create(any(ProjectDto.class))).thenReturn(projectDto);

        ResultActions response = mockMvc.perform(post("/project")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(projectDto)));

            response.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", CoreMatchers.is(projectDto.getId().intValue())))
                    .andExpect(jsonPath("$.name", CoreMatchers.is(projectDto.getName())));
    }

	@Test
    void ProjectController_UpdateProject_ReturnOk() throws Exception {
        when(projectService.update(any(ProjectDto.class), eq(1L))).thenReturn(projectDto);

        ResultActions response = mockMvc.perform(put("/project/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDto)));

        response.andExpect(status().isOk());
    }

	@Test
    void ProjectController_UpdateProject_NotFound() throws Exception {
        when(projectService.update(any(ProjectDto.class), eq(1L))).thenThrow(new NotFoundException("Project not found with ID: 1"));

        ResultActions response = mockMvc.perform(put("/project/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

	@Test
	void ProjectController_GetProject_Success() throws Exception {
	    when(projectService.findById(1L)).thenReturn(Optional.of(project));

	    ResultActions response = mockMvc.perform(get("/project/1")
	            .contentType(MediaType.APPLICATION_JSON));

	    response.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(project.getId().intValue())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(project.getName())));
	}

	@Test
	void ProjectController_GetProject_NotFound() throws Exception {
	    when(projectService.findById(1L)).thenReturn(Optional.empty());

	    ResultActions response = mockMvc.perform(get("/project/1")
	            .contentType(MediaType.APPLICATION_JSON));

	    response.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
    @Test
    void ProjectController_DeleteProjectById_ReturnNoContent() throws Exception {
        ResultActions response = mockMvc.perform(delete("/project/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }

    @Test
    void ProjectController_FindAllProjects_ReturnOk() throws Exception {
        List<ProjectDto> projectDtos = Arrays.asList(projectDto);
        when(projectService.findAll()).thenReturn(projectDtos);

        ResultActions response = mockMvc.perform(get("/project/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(projectDto.getId().intValue())))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is(projectDto.getName())));
    }
}
