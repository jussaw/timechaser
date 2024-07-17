package com.timechaser.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.timechaser.entity.Project;
import com.timechaser.security.UserRoles;
import com.timechaser.service.ProjectService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = {UserRoles.ADMIN})
public class ProjectControllerTest {
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
    }

    @Test
    void ProjectController_CreateProject_ReturnCreated() throws Exception {
        // stuff
    }
}
