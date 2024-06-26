package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.exception.RoleNotFoundException;
import com.timechaser.service.RoleService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = RoleController.class)
public class RoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("Admin");

        roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("Admin");
    }

    @Test
    public void RoleController_FindRoleById_ReturnOk() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(role));

        ResultActions response = mockMvc.perform(get("/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    public void RoleController_FindRoleById_NotFound() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void RoleController_FindRoleByName_ReturnOk() throws Exception {
        when(roleService.findByName("Admin")).thenReturn(Optional.of(role));

        ResultActions response = mockMvc.perform(get("/role/name/Admin")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    public void RoleController_FindRoleByName_NotFound() throws Exception {
        when(roleService.findByName("Admin")).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/role/name/Admin")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void RoleController_FindAllRoles_ReturnOk() throws Exception {
        List<RoleDto> roleDtos = Arrays.asList(roleDto);
        when(roleService.findAll()).thenReturn(roleDtos);

        ResultActions response = mockMvc.perform(get("/role/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    public void RoleController_CreateRole_ReturnCreated() throws Exception {
        when(roleService.create(any(RoleDto.class))).thenReturn(roleDto);

        ResultActions response = mockMvc.perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }

    @Test
    public void RoleController_UpdateRole_ReturnOk() throws Exception {
        when(roleService.update(any(RoleDto.class), eq(1L))).thenReturn(roleDto);

        ResultActions response = mockMvc.perform(put("/role/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(roleDto.getName())));
    }
    
    @Test
    public void RoleController_UpdateRole_NotFound() throws Exception {
        when(roleService.update(any(RoleDto.class), eq(1L))).thenThrow(new RoleNotFoundException("Role not found with ID: 1"));

        ResultActions response = mockMvc.perform(put("/role/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        response.andExpect(status().isNotFound());
    }
    
    @Test
    public void RoleController_DeleteRoleById_ReturnNoContent() throws Exception {
        ResultActions response = mockMvc.perform(delete("/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }
}
