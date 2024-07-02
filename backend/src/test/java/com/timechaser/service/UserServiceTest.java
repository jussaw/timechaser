package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserDetailsResponse;
import com.timechaser.entity.User;
import com.timechaser.exception.UserCreationException;
import com.timechaser.exception.UserNotFoundException;
import com.timechaser.exception.UserUpdateDetailsException;
import com.timechaser.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	private UserService userService;

	private CreateUserRequest createUserRequest;
	private UpdateUserDetailsRequest updateUserDetailsRequest;
	private User user;
	private Optional<User> optionalUser;

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

		userService = new UserService(userRepository, passwordEncoder);

		optionalUser = Optional.of(new User(createUserRequest));

		updateUserDetailsRequest = new UpdateUserDetailsRequest();
		updateUserDetailsRequest.setUsername("newuser");
		updateUserDetailsRequest.setFirstName("newfirst");
		updateUserDetailsRequest.setLastName("newlast");
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
	public void UserService_Delete_Success() {
		userService.deleteById(4L);
		
		verify(userRepository, times(1)).deleteById(4L);
	}
	
	@Test
	public void UserService_findById_Success() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		User result = userService.findById(user.getId()).get();
		
		assertNotNull(result);
		assertEquals(user.getUsername(), result.getUsername());
		assertEquals(user.getLastName(), result.getLastName());
		assertEquals(user.getUsername(), result.getUsername());
		assertEquals(user.getId(), result.getId());
		
		verify(userRepository, times(1)).findById(user.getId());
	}
	
	@Test
	public void UserService_Update_Success() {
		when(userService.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		UpdateUserDetailsResponse response = userService.updateDetails(1L, updateUserDetailsRequest);
		
		assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
	}
	
	@Test
	public void UserService_Update_400() {
		when(userService.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenThrow(new IllegalArgumentException());
		
		assertThatThrownBy(() ->  userService.updateDetails(user.getId(), updateUserDetailsRequest))
		.isInstanceOf(UserUpdateDetailsException.class);
	}
	
	@Test
	public void UserService_Update_404() {
		when(userService.findById(anyLong())).thenThrow(new UserNotFoundException("testing123"));
		
		assertThatThrownBy(() ->  userService.findById(2L))
		.isInstanceOf(UserNotFoundException.class);
	}
}
