package com.timechaser.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.LoginRequest;
import com.timechaser.dto.LoginResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.mapper.RoleMapper;
import com.timechaser.security.MyUserDetails;
import com.timechaser.util.JwtTokenUtil;

@RestController 
@RequestMapping("/auth")
public class AuthenticationController {
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtUtil;
        
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)  {
    	logger.info("Received request to generate JWT for username: {}", loginRequest.getUsername());
    	
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        
        String token = jwtUtil.generateAccessToken(user.getUsername());
        
        List<RoleDto> roleDtos = user.getUser()
        		.getRoles()
        		.stream()
        		.map(RoleMapper::toDto)
        		.collect(Collectors.toList());
        
        LoginResponse loginRes = new LoginResponse(token, new CreateUserResponse(user.getUser()), roleDtos);

        return ResponseEntity.ok(loginRes);
    }
}
