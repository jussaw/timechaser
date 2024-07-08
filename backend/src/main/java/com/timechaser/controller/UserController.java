package com.timechaser.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timechaser.dto.AddRoleDto;
import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserDetailsResponse;
import com.timechaser.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		
		logger.info("Received request to create user with username {}", request.getUsername());

		CreateUserResponse response = userService.create(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@PathVariable("id") Long id) {

		logger.info("Received request to delete user with id {}", id);
		
		userService.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping(value = "{id}/role", consumes = "application/json")
    public ResponseEntity addRoleToUser(@PathVariable Long id, @RequestBody @Valid AddRoleDto addRoleDto) {
		logger.info("Received request to add role ID {} to user ID {}", addRoleDto.getRoleId(), id);
		
        userService.addRoleToUser(id, addRoleDto.getRoleId());
        
        return ResponseEntity.ok().build();
    }
	
	@DeleteMapping("{userId}/role/{roleId}")
    public ResponseEntity deleteRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
		logger.info("Received request to remove role ID {} from user ID {}", roleId, userId);
		
        userService.removeRoleFromUser(userId, roleId);
        
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("{id}/role")
    public ResponseEntity<List<RoleDto>> getRolesForUser(@PathVariable Long id) {
		logger.info("Received request to get roles to user ID {}", id);
		
        List<RoleDto> roles = userService.findRolesForUser(id);
        
        return ResponseEntity.ok().body(roles);
    }

	@PutMapping("/{id}")
	public ResponseEntity<UpdateUserDetailsResponse> updateUserDetails(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserDetailsRequest request) {
		
		logger.info("Received request to update user with {}", id);
		
		UpdateUserDetailsResponse response = userService.updateDetails(id, request);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
}
