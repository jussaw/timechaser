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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.enums.UserRoles;
import com.timechaser.exception.NotFoundException;
import com.timechaser.repository.UserRepository;
import com.timechaser.service.RoleService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = {UserRoles.ADMIN})
public class RoleControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
    MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        role = new Role();
        role.setId(1L);
        role.setName("Admin");

        roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("Admin");
    }

    @Test
    void RoleController_FindRoleById_ReturnOk() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(role));

        ResultActions response = mockMvc.perform(get("/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    void RoleController_FindRoleById_NotFound() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }

    @Test
    void RoleController_FindRoleByName_ReturnOk() throws Exception {
        when(roleService.findByName("Admin")).thenReturn(Optional.of(role));

        ResultActions response = mockMvc.perform(get("/role/name/Admin")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    void RoleController_FindRoleByName_NotFound() throws Exception {
        when(roleService.findByName("Admin")).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/role/name/Admin")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }

    @Test
    void RoleController_FindAllRoles_ReturnOk() throws Exception {
        List<RoleDto> roleDtos = Arrays.asList(roleDto);
        when(roleService.findAll()).thenReturn(roleDtos);

        ResultActions response = mockMvc.perform(get("/role/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    void RoleController_CreateRole_ReturnCreated() throws Exception {
        when(roleService.create(any(RoleDto.class))).thenReturn(roleDto);

        ResultActions response = mockMvc.perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    void RoleController_UpdateRole_ReturnOk() throws Exception {
        when(roleService.update(any(RoleDto.class), eq(1L))).thenReturn(roleDto);

        ResultActions response = mockMvc.perform(put("/role/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        response.andExpect(status().isOk());
    }
    
    @Test
    void RoleController_UpdateRole_NotFound() throws Exception {
        when(roleService.update(any(RoleDto.class), eq(1L))).thenThrow(new NotFoundException("Role not found with ID: 1"));

        ResultActions response = mockMvc.perform(put("/role/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        response.andExpect(status().isNotFound());
    }
    
    @Test
    void RoleController_DeleteRoleById_ReturnNoContent() throws Exception {
        ResultActions response = mockMvc.perform(delete("/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }
}
