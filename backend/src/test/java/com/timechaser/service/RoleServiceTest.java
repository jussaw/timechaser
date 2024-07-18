package com.timechaser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.exception.NotFoundException;
import com.timechaser.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

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
    void RoleService_FindById_Success() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        
        Optional<Role> result = roleService.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(role.getId(), result.get().getId());
        assertEquals(role.getName(), result.get().getName());
    }

    @Test
    void RoleService_FindByName_Success() {
        when(roleRepository.findByName("Admin")).thenReturn(Optional.of(role));
        
        Optional<Role> result = roleService.findByName("Admin");
        
        assertTrue(result.isPresent());
        assertEquals(role.getId(), result.get().getId());
        assertEquals(role.getName(), result.get().getName());
    }

    @Test
    void RoleService_FindAll_Success() {
        List<Role> roles = Arrays.asList(role);
        when(roleRepository.findAll()).thenReturn(roles);
        
        List<RoleDto> result = roleService.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roleDto.getId(), result.get(0).getId());
        assertEquals(roleDto.getName(), result.get(0).getName());
    }

    @Test
    void RoleService_Create_Success() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        
        RoleDto result = roleService.create(roleDto);
        
        assertNotNull(result);
        assertEquals(roleDto.getId(), result.getId());
        assertEquals(roleDto.getName(), result.getName());
    }

    @Test
    void RoleService_UpdateRole_Success() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        
        RoleDto result = roleService.update(roleDto, 1L);
        
        assertNotNull(result);
        assertEquals(roleDto.getId(), result.getId());
        assertEquals(roleDto.getName(), result.getName());
    }
    
    @Test
    void RoleService_UpdateRole_RoleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(NotFoundException.class, () -> {
            roleService.update(roleDto, 1L);
        });
        
        String expectedMessage = "Role not found with ID: 1";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void RoleService_DeleteById_Success() {
        doNothing().when(roleRepository).deleteById(1L);
        
        roleService.deleteById(1L);
        
        verify(roleRepository, times(1)).deleteById(1L);
    }
}
