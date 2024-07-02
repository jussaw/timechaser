package com.timechaser.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.entity.User;
import com.timechaser.exception.RoleNotFoundException;
import com.timechaser.exception.UserCreationException;
import com.timechaser.mapper.RoleMapper;
import com.timechaser.repository.RoleRepository;
import com.timechaser.repository.UserRepository;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		super();
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
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + userId + " was not found."));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID: " + roleId + " was not found."));

        user.getRoles().add(role);
        userRepository.save(user);
    }
	
	@Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
		logger.info("Removing roleID: {} from userID: {}", roleId, userId);
		
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + userId + " was not found."));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID: " + roleId + " was not found."));

        user.getRoles().remove(role);
        userRepository.save(user);
    }
	
    public List<RoleDto> findRolesForUser(Long userId) {
		logger.info("Finding roles for user ID: {}", userId);
		
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + userId + " was not found."));

        return user.getRoles()
        		.stream()
        		.map(RoleMapper::toDto)
        		.collect(Collectors.toList());
    }

}
