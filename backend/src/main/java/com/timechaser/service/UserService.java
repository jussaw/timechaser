package com.timechaser.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserDetailsResponse;
import com.timechaser.entity.User;
import com.timechaser.exception.UserCreationException;
import com.timechaser.exception.UserNotFoundException;
import com.timechaser.exception.UserUpdateDetailsException;
import com.timechaser.repository.UserRepository;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public CreateUserResponse create(CreateUserRequest request) {
		logger.info("Creating user with username {}", request.getUsername());
		try {
			User user = new User(request);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			user = userRepository.save(user);

			return new CreateUserResponse(user);

		} catch (Exception e) {
			logger.error("Error occured while creating user with username {}", request.getUsername());
			throw new UserCreationException("Failed to create user", e);
		}
	}

	@Transactional
	public void deleteById(Long id) {
		logger.info("Deleting user with id {}", id);

		userRepository.deleteById(id);
	}
	
	@Transactional
	public UpdateUserDetailsResponse updateDetails(Long id, UpdateUserDetailsRequest request) {
		try {
			User user = findById(id);
			user.setUsername(request.getUsername());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			
			user = userRepository.save(user);
			
			return new UpdateUserDetailsResponse(user);
	
		} catch (Exception e) {
			logger.error("Error while updating user details");
			throw new UserUpdateDetailsException("Failed to update user");
		} 
	}
	
	public User findById(Long id) {
		try {
			User user = userRepository.findById(id).get();
			return user;
		}
		catch (Exception e) {
			logger.error("Cannot find user with id {}", id);
			throw new UserNotFoundException("Failed to update user");
		}
	}
}
