package com.timechaser.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.timechaser.entity.Role;
import com.timechaser.entity.User;
import com.timechaser.security.UserRoles;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AuthorizationService authorizationService;

    private User user;
    private Role roleAdmin;
    private Role roleEmployee;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        roleAdmin = new Role();
        roleAdmin.setName(UserRoles.ADMIN);

        roleEmployee = new Role();
        roleEmployee.setName(UserRoles.EMPLOYEE);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getUsername());
    }

    @Test
    void isAdminOrSelf_UserIsAdmin_ReturnsTrue() {
        user.getRoles().add(roleAdmin);
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean result = authorizationService.isAdminOrSelf(user.getId());

        assertTrue(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void isAdminOrSelf_UserIsSelf_ReturnsTrue() {
        user.getRoles().add(roleEmployee);
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean result = authorizationService.isAdminOrSelf(user.getId());

        assertTrue(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void isAdminOrSelf_UserIsNeitherAdminNorSelf_ReturnsFalse() {
        user.getRoles().add(roleEmployee);
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean result = authorizationService.isAdminOrSelf(2L);

        assertFalse(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void isAdminOrSelf_UserNotFound_ReturnsFalse() {
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        boolean result = authorizationService.isAdminOrSelf(user.getId());

        assertFalse(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void isSelf_UserIsSelf_ReturnsTrue() {
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean result = authorizationService.isSelf(user.getId());

        assertTrue(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void isSelf_UserIsNotSelf_ReturnsFalse() {
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean result = authorizationService.isSelf(2L);

        assertFalse(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void isSelf_UserNotFound_ReturnsFalse() {
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        boolean result = authorizationService.isSelf(user.getId());

        assertFalse(result);
        verify(userService, times(1)).findByUsername(user.getUsername());
    }
}
