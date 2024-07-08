package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.AddRoleDto;
import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserDetailsResponse;
import com.timechaser.exception.AccessDeniedException;
import com.timechaser.exception.UserCreationException;
import com.timechaser.repository.UserRepository;
import com.timechaser.exception.UserNotFoundException;
import com.timechaser.exception.UserUpdateDetailsException;
import com.timechaser.service.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
    private ObjectMapper objectMapper;

	private CreateUserRequest request;
	
    private AddRoleDto addRoleDto;
    private RoleDto roleDto;

    @BeforeEach
    void setup() {
        request = new CreateUserRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setPassword("password");
        request.setUsername("username");
        
        addRoleDto = new AddRoleDto();
        addRoleDto.setRoleId(1L);
        
        roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("Admin");
    }

	
	@Test
	void UserController_CreateUser_ReturnCreated() throws Exception{
		CreateUserResponse responseDto = new CreateUserResponse();
		responseDto.setFirstName("First");
		responseDto.setLastName("Last");
		responseDto.setUsername("username");
		responseDto.setId(1L);
		
		when(userService.create(any(CreateUserRequest.class))).thenReturn(responseDto);
		
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
		// Asserting the response expectations
	    response.andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(responseDto.getFirstName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(responseDto.getLastName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is( responseDto.getId().intValue())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(responseDto.getUsername())));

	}
	
	@Test
	void UserController_CreateUser_UserCreationFailure() throws Exception{
		
		when(userService.create(any(CreateUserRequest.class))).thenThrow(new UserCreationException("Faiied to create user", new Exception()));
		
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_CreateUser_NoUsername_400() throws Exception{
		
		request.setUsername(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_CreateUser_NoPassword_400() throws Exception{
		
		request.setPassword(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_CreateUser_NoFirstName_400() throws Exception{
		
		request.setFirstName(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_CreateUser_NoLastName_400() throws Exception{
		
		request.setLastName(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_Delete_User_Success() throws Exception {	
		ResultActions response = mockMvc.perform(delete("/user/8")
			.contentType(MediaType.APPLICATION_JSON));
		
	    response.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
    void UserController_AddRoleToUser_Success() throws Exception {

        ResultActions response = mockMvc.perform(post("/user/1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addRoleDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    void UserController_RemoveRoleFromUser_Success() throws Exception {

        ResultActions response = mockMvc.perform(delete("/user/1/role/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    
    @Test
    void UserController_GetRolesForUser_Success() throws Exception {
        List<RoleDto> roles = Collections.singletonList(roleDto);
        when(userService.findRolesForUser(1L)).thenReturn(roles);

        ResultActions response = mockMvc.perform(get("/user/1/role")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(roles.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(roleDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(roleDto.getName())));
    }	
    
	@Test
	void UserController_Update_User_Details_Success() throws Exception {
		UpdateUserDetailsResponse responseDto = new UpdateUserDetailsResponse();
		responseDto.setFirstName("newfirst");
		responseDto.setLastName("newlast");
		
		when(userService.updateDetails(anyLong(), any(UpdateUserDetailsRequest.class))).thenReturn(responseDto);
		
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
		// Asserting the response expectations
	    response.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(responseDto.getFirstName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(responseDto.getLastName())));

	}
	
	@Test
	void UserController_Update_User_Details_500() throws Exception{
		
		when(userService.updateDetails(anyLong(), any(UpdateUserDetailsRequest.class))).thenThrow(new UserUpdateDetailsException("testing123"));
		
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}
	
	@Test
	void UserController_Update_User_Details_404() throws Exception{
		
		when(userService.updateDetails(anyLong(), any(UpdateUserDetailsRequest.class))).thenThrow(new UserNotFoundException("testing123"));
		
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	void UserController_Update_User_Details_NoFirstName() throws Exception{
		
		request.setFirstName(null);
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_Update_User_Details_NoLastName() throws Exception{
		
		request.setLastName(null);
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
}
