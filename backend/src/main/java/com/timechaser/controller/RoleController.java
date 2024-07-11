package com.timechaser.controller;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.exception.RoleNotFoundException;
import com.timechaser.mapper.RoleMapper;
import com.timechaser.service.RoleService;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole(T(com.timechaser.security.UserRoles).ADMIN)")
public class RoleController {
	private final Logger logger = LoggerFactory.getLogger(RoleController.class);
	private final RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleDto> findRoleById(@PathVariable Long id){
		logger.info("Received request to get role by ID: {}", id);
		
		Optional<Role> roleOptional = roleService.findById(id);
		
		RoleDto response = roleOptional
		        .map(RoleMapper::toDto)
		        .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<RoleDto> findRoleByName(@PathVariable String name){
		logger.info("Received request to get role by name: {}", name);
		
		Optional<Role> roleOptional = roleService.findByName(name);
		
		RoleDto response = roleOptional
		        .map(RoleMapper::toDto)
		        .orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + name));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<RoleDto>> findAllRoles(){
		logger.info("Received request to get all roles");
		
		List<RoleDto> roleDtos = roleService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(roleDtos);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
		logger.info("Received request to create role with name: {}", roleDto.getName());
		
		roleDto = roleService.create(roleDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(roleDto);

	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto){
		logger.info("Received request to update role with id: {}", id);
		
		roleDto = roleService.update(roleDto, id);
		
		return ResponseEntity.status(HttpStatus.OK).body(roleDto);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRoleById(@PathVariable Long id){
		logger.info("Received request to delete role with id: {}", id);
			
		roleService.deleteById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
