package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.LoginRequest;
import com.timechaser.entity.Role;
import com.timechaser.entity.User;
import com.timechaser.security.MyUserDetails;
import com.timechaser.util.JwtTokenUtil;

@SpringBootTest
@ContextConfiguration
public class AuthControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
    MockMvc mockMvc;


    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequest loginRequest;
    private MyUserDetails userDetails;
    private String token;
    private List<Role> roleList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        loginRequest = new LoginRequest("testuser", "password");
        
        roleList = Collections.singletonList(Role.builder().name("role").id(1L).build());

        HashSet<Role> roles = new HashSet<Role>(roleList);
        
        userDetails = new MyUserDetails(User.builder()
        		.id(1L)
        		.username("testuser")
        		.password("password")
        		.roles(roles)
        		.build());
        
        token = "testToken";
    }

    @Test
    void AuthController_Login_ReturnOk() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateAccessToken(userDetails.getUsername())).thenReturn(token);

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.token", CoreMatchers.is(token)))
                .andExpect(jsonPath("$.user.id", CoreMatchers.is(userDetails.getUser().getId().intValue())))
                .andExpect(jsonPath("$.user.username", CoreMatchers.is(userDetails.getUser().getUsername())))
                .andExpect(jsonPath("$.roles[0].id", CoreMatchers.is(roleList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.roles[0].name", CoreMatchers.is(roleList.get(0).getName())));

    }

    @Test
    void AuthController_Login_BadCredentials() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(status().isUnauthorized());
    }
}
