package com.timechaser.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.entity.User;
import com.timechaser.exception.UserCreationException;
import com.timechaser.exception.UserNotFoundException;
import com.timechaser.repository.UserRepository;

import net.bytebuddy.asm.Advice.Thrown;

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
	public Optional<User> delete(Long id) throws UserNotFoundException {
		logger.info("Deleting user with id {}", id);

		Optional<User> user = userRepository.findById(id);

		if (user.get() == null)
			throw new UserNotFoundException("User was not found");

		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			logger.info("Error occured while deleting user with id {}", id);
		}

		return user;
	}

}
