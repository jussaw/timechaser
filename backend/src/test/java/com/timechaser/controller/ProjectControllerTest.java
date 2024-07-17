package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.ProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.mapper.ProjectMapper;
import com.timechaser.security.UserRoles;
import com.timechaser.service.ProjectService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = {UserRoles.ADMIN})
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
}
