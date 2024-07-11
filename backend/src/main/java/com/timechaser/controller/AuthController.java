package com.timechaser.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.LoginRequest;
import com.timechaser.dto.LoginResponse;
import com.timechaser.entity.User;
import com.timechaser.security.MyUserDetails;
import com.timechaser.util.JwtTokenUtil;

@RestController 
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtUtil;
    
    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginReq)  {
 
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        
        String token = jwtUtil.generateAccessToken(user.getUsername());
        
        LoginResponse loginRes = new LoginResponse(new CreateUserResponse(user.getUser()), token);

        return ResponseEntity.ok(loginRes);

    }
}
