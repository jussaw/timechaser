package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.entity.User;
import com.timechaser.exception.RoleNotFoundException;
import com.timechaser.exception.UserCreationException;
import com.timechaser.repository.RoleRepository;
import com.timechaser.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private RoleRepository roleRepository;

    @InjectMocks
	private UserService userService;

	private CreateUserRequest createUserRequest;
	private User user;
	private Role role;
	private Optional<User> optionalUser;
	private Optional<Role> optionalRole;

	@BeforeEach
	void setUp() {
		createUserRequest = new CreateUserRequest();
		createUserRequest.setUsername("testuser");
		createUserRequest.setPassword("password");
		createUserRequest.setFirstName("First");
		createUserRequest.setLastName("Last");

		user = new User();
		user.setUsername(createUserRequest.getUsername());
		user.setPassword(createUserRequest.getPassword());
		user.setId(1L);
		user.setFirstName(createUserRequest.getFirstName());
		user.setLastName(createUserRequest.getLastName());		

		optionalUser = Optional.of(new User(createUserRequest));

        role = new Role();
        role.setId(1L);
        role.setName("Admin");

        optionalUser = Optional.of(user);
        optionalRole = Optional.of(role);

	}

	@Test
	void UserService_Create_Success() {
		when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		CreateUserResponse response = userService.create(createUserRequest);
		
		assertNotNull(response);
		assertEquals(user.getUsername(), response.getUsername());
		assertEquals(user.getLastName(), response.getLastName());
		assertEquals(user.getUsername(), response.getUsername());
		assertEquals(user.getId(), response.getId());
		verify(passwordEncoder, times(1)).encode(createUserRequest.getPassword());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void UserService_Create_Failure() {
		when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenThrow(new IllegalArgumentException());
		
		assertThatThrownBy(() ->  userService.create(createUserRequest))
			.isInstanceOf(UserCreationException.class);
	}

	@Test
	void UserService_Delete_Success() {
		userService.deleteById(4L);
		
		verify(userRepository, times(1)).deleteById(4L);
	}
	
	@Test
    void UserService_addRoleToUser_Success() {
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        when(roleRepository.findById(1L)).thenReturn(optionalRole);

        userService.addRoleToUser(1L, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void UserService_addRoleToUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.addRoleToUser(1L, 1L))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with ID: 1 was not found.");
    }

    @Test
    void UserService_addRoleToUser_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.addRoleToUser(1L, 1L))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage("Role with ID: 1 was not found.");
    }

    @Test
    void UserService_removeRoleFromUser_Success() {
        user.getRoles().add(role);
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        when(roleRepository.findById(1L)).thenReturn(optionalRole);

        userService.removeRoleFromUser(1L, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        assertFalse(user.getRoles().contains(role));
    }

    @Test
    void UserService_removeRoleFromUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.removeRoleFromUser(1L, 1L))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with ID: 1 was not found.");
    }

    @Test
    void UserService_removeRoleFromUser_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.removeRoleFromUser(1L, 1L))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage("Role with ID: 1 was not found.");
    }

    @Test
    void UserService_findRolesForUser_Success() {
        user.getRoles().add(role);
        when(userRepository.findById(1L)).thenReturn(optionalUser);

        List<RoleDto> roles = userService.findRolesForUser(1L);

        verify(userRepository, times(1)).findById(1L);
        assertEquals(1, roles.size());
        assertEquals("Admin", roles.get(0).getName());
    }

    @Test
    void UserService_findRolesForUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findRolesForUser(1L))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with ID: 1 was not found.");
    }
}
