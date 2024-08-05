package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserPasswordRequest;
import com.timechaser.dto.UserDto;
import com.timechaser.entity.Role;
import com.timechaser.entity.User;
import com.timechaser.exception.CreateException;
import com.timechaser.exception.NotFoundException;
import com.timechaser.exception.UserUpdateDetailsException;
import com.timechaser.exception.UserUpdatePasswordException;
import com.timechaser.mapper.UserMapper;
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
	private UpdateUserDetailsRequest updateUserDetailsRequest;
	private UpdateUserPasswordRequest updateUserPasswordRequest;
	private User user;
	private UserDto userDto;
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
		user.setPto(120);
		user.setFirstName(createUserRequest.getFirstName());
		user.setLastName(createUserRequest.getLastName());	
		
		userDto = UserMapper.toDto(user);

		optionalUser = Optional.of(new User(createUserRequest));

        role = new Role();
        role.setId(1L);
        role.setName("Admin");

        optionalUser = Optional.of(user);
        optionalRole = Optional.of(role);

		updateUserDetailsRequest = new UpdateUserDetailsRequest();
		updateUserDetailsRequest.setFirstName("newfirst");
		updateUserDetailsRequest.setLastName("newlast");
		
		updateUserPasswordRequest = new UpdateUserPasswordRequest();
		updateUserPasswordRequest.setPassword("password");
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
		assertEquals(user.getPto(), response.getPto());
		verify(passwordEncoder, times(1)).encode(createUserRequest.getPassword());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void UserService_Create_Failure() {
		when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenThrow(new IllegalArgumentException());
		
		assertThatThrownBy(() ->  userService.create(createUserRequest))
			.isInstanceOf(CreateException.class);
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
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with ID: 1 was not found.");
    }

    @Test
    void UserService_addRoleToUser_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.addRoleToUser(1L, 1L))
                .isInstanceOf(NotFoundException.class)
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
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with ID: 1 was not found.");
    }

    @Test
    void UserService_removeRoleFromUser_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.removeRoleFromUser(1L, 1L))
                .isInstanceOf(NotFoundException.class)
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
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with ID: 1 was not found.");
    }
    
    @Test
	void UserService_findById_Success() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		User result = userService.findById(user.getId()).get();
		
		assertNotNull(result);
		assertEquals(user.getUsername(), result.getUsername());
		assertEquals(user.getLastName(), result.getLastName());
		assertEquals(user.getUsername(), result.getUsername());
		assertEquals(user.getPto(), result.getPto());
		assertEquals(user.getId(), result.getId());
		verify(userRepository, times(1)).findById(user.getId());
	}
    
	@Test
	void UserService_findById_404() {
		when(userService.findById(anyLong())).thenThrow(new NotFoundException("testing123"));
		
		assertThatThrownBy(() ->  userService.findById(2L))
		.isInstanceOf(NotFoundException.class);
	}
	
	@Test
	void UserService_Update_Details_Success() {
		when(userService.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		userService.updateDetails(1L, updateUserDetailsRequest);
		
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
	}
	
	@Test
	void UserService_Update_Details_400() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenThrow(new UserUpdateDetailsException("testing123"));
		
		assertThatThrownBy(() ->  userService.updateDetails(user.getId(), updateUserDetailsRequest))
		.isInstanceOf(UserUpdateDetailsException.class);
	}
	
	@Test
	void UserService_Update_Password_Success() {
		when(userService.findById(anyLong())).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		userService.updatePassword(1L, updateUserPasswordRequest);
		
        verify(userRepository, times(1)).findById(user.getId());
        verify(passwordEncoder, times(1)).encode(updateUserPasswordRequest.getPassword());
        verify(userRepository, times(1)).save(user);
	}
	
	@Test
	void UserService_FindAll_Success() {
		List<User> users = Arrays.asList(user);
		when(userRepository.findAll()).thenReturn(users);
		
		List<UserDto> result = userService.findAll();
		
		assertNotNull(result);

        assertEquals(1, result.size());
        assertEquals(userDto.getId(),  result.get(0).getId());
        assertEquals(userDto.getUsername(), result.get(0).getUsername());
	}
	
	@Test
	void UserService_Update_Password_400() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenThrow(new UserUpdateDetailsException("testing123"));
		
		assertThatThrownBy(() ->  userService.updatePassword(user.getId(), updateUserPasswordRequest))
		.isInstanceOf(UserUpdatePasswordException.class);
	}
}
