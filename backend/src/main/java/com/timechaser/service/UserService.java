package com.timechaser.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserPasswordRequest;
import com.timechaser.entity.User;
import com.timechaser.exception.RoleNotFoundException;
import com.timechaser.exception.UserCreationException;
import com.timechaser.exception.UserNotFoundException;
import com.timechaser.mapper.RoleMapper;
import com.timechaser.repository.RoleRepository;
import com.timechaser.exception.UserUpdateDetailsException;
import com.timechaser.exception.UserUpdatePasswordException;
import com.timechaser.repository.UserRepository;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
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
    public void addRoleToUser(Long userId, Long roleId) {
		logger.info("Adding roleID: {} to userID: {}", roleId, userId);
		
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " was not found."));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID: " + roleId + " was not found."));

        user.getRoles().add(role);
        userRepository.save(user);
    }
	
	@Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
		logger.info("Removing roleID: {} from userID: {}", roleId, userId);
		
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " was not found."));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID: " + roleId + " was not found."));

        user.getRoles().remove(role);
        userRepository.save(user);
    }
	
    public List<RoleDto> findRolesForUser(Long userId) {
		logger.info("Finding roles for user ID: {}", userId);
		
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " was not found."));

        return user.getRoles()
        		.stream()
        		.map(RoleMapper::toDto)
        		.collect(Collectors.toList());
    }
    
	public void updateDetails(Long id, UpdateUserDetailsRequest request) {
		logger.info("Updating user details with id {}", id);
		
		try {
			User user = findById(id).orElseThrow(() -> new UserNotFoundException("Unable to find user with id: " + id));
			
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			
			user = userRepository.save(user);
	
		} catch (UserNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error while updating user details", e);
			throw new UserUpdateDetailsException("Failed to update user details");
		} 
	}
	
	public void updatePassword(Long id, UpdateUserPasswordRequest request) {
		logger.info("Updating user password with id {}", id);
		
		try {
			User user = findById(id).orElseThrow(() -> new UserNotFoundException("Unable to find user with id: " + id));
			
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			
			user = userRepository.save(user);
	
		} catch (UserNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error while updating user password", e);
			throw new UserUpdatePasswordException("Failed to update user password");
		} 
	}
	
	public Optional<User> findById(Long id) {
		logger.info("Finding user with id {}", id);
		
		Optional<User> user = userRepository.findById(id);
		
		return user;
	}
}
