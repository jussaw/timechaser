package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.entity.User;
import com.timechaser.exception.UserCreationException;
import com.timechaser.exception.UserNotFoundException;
import com.timechaser.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	private UserService userService;

	private CreateUserRequest createUserRequest;
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
	public void deleteSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		
		userService.delete(6L);
		
		assertEquals("testuser", optionalUser.get().getUsername()); 
	}

	@Test
	public void deleteUserNotFound() {
		when(userRepository.findById(anyLong())).thenReturn(null);
		
		assertThatThrownBy(() -> userService.delete(5L))
			.isInstanceOf(UserNotFoundException.class);
		
	}

	@Test
	public void deleteException() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		
		assertThatThrownBy(() -> userService.delete(null))
			.isInstanceOf(Exception.class);
	}
}
