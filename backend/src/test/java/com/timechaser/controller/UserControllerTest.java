package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserDetailsResponse;
import com.timechaser.exception.AccessDeniedException;
import com.timechaser.exception.UserCreationException;
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
	
	@Autowired
    private ObjectMapper objectMapper;

	private CreateUserRequest request;
	
	@BeforeEach
	void setup() {
		request = new CreateUserRequest();
		request.setFirstName("First");
		request.setLastName("Last");
		request.setPassword("password");
		request.setUsername("username");
	}
	
	@Test
	public void UserController_CreateUser_ReturnCreated() throws Exception{
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
	public void UserController_CreateUser_UserCreationFailure() throws Exception{
		
		when(userService.create(any(CreateUserRequest.class))).thenThrow(new UserCreationException("Faiied to create user", new Exception()));
		
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_CreateUser_NoUsername_400() throws Exception{
		
		request.setUsername(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_CreateUser_NoPassword_400() throws Exception{
		
		request.setPassword(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_CreateUser_NoFirstName_400() throws Exception{
		
		request.setFirstName(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_CreateUser_NoLastName_400() throws Exception{
		
		request.setLastName(null);
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_Delete_User_Success() throws Exception {	
		ResultActions response = mockMvc.perform(delete("/user/8")
			.contentType(MediaType.APPLICATION_JSON));
		
	    response.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void UserController_Update_User_Details_Success() throws Exception {
		UpdateUserDetailsResponse responseDto = new UpdateUserDetailsResponse();
		responseDto.setFirstName("newfirst");
		responseDto.setLastName("newlast");
		responseDto.setUsername("newuser");
		
		when(userService.updateDetails(anyLong(), any(UpdateUserDetailsRequest.class))).thenReturn(responseDto);
		
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
		// Asserting the response expectations
	    response.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(responseDto.getFirstName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(responseDto.getLastName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(responseDto.getUsername())));

	}
	
	@Test
	public void UserController_Update_User_Details_500() throws Exception{
		
		when(userService.updateDetails(anyLong(), any(UpdateUserDetailsRequest.class))).thenThrow(new UserUpdateDetailsException("testing123"));
		
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}
	
	@Test
	public void UserController_Update_User_Details_404() throws Exception{
		
		when(userService.updateDetails(anyLong(), any(UpdateUserDetailsRequest.class))).thenThrow(new UserNotFoundException("testing123"));
		
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	public void UserController_Update_User_Details_NoUsername() throws Exception{
		
		request.setUsername(null);
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_Update_User_Details_NoFirstName() throws Exception{
		
		request.setFirstName(null);
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void UserController_Update_User_Details_NoLastName() throws Exception{
		
		request.setLastName(null);
		ResultActions response = mockMvc.perform(put("/user/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
}
