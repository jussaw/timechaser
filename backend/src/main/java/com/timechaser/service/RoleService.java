package com.timechaser.service;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;
import com.timechaser.exception.RoleNotFoundException;
import com.timechaser.mapper.RoleMapper;
import com.timechaser.repository.RoleRepository;


@Service
public class RoleService {
	private final Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Optional<Role> findById(Long id) {
		logger.info("Attempting to retrieve role with ID: {}", id);
		return roleRepository.findById(id);
	}
	
	public Optional<Role> findByName(String name) {
		logger.info("Attempting to retrieve role with name: {}", name);
		return roleRepository.findByName(name);
	}
	
    public List<RoleDto> findAll() {
    	logger.info("Attempting to find all roles");
    	
        List<Role> roles = roleRepository.findAll();
        
        return roles.stream()
        		.map(RoleMapper::toDto)
        		.toList();
    }
	
	@Transactional
	public RoleDto create(RoleDto roleDto) {
		logger.info("Create role with name: {}", roleDto.getName());
		
		Role role = RoleMapper.toEntity(roleDto);
		
		role = roleRepository.save(role);
		
		return RoleMapper.toDto(role);
	}
	
	@Transactional
	public RoleDto update(RoleDto roleDto, Long id) {
		logger.info("Updating role with ID: {}", id);
		
		Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
		
		existingRole.setName(roleDto.getName());
		existingRole = roleRepository.save(existingRole);
		
		return RoleMapper.toDto(existingRole);
	}
	
	@Transactional
    public void deleteById(Long id) {
		logger.info("Deleting Role with ID: {}", id);
		
        roleRepository.deleteById(id);
    }
}
