package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.AddRoleDto;
import com.timechaser.dto.CreateUserRequest;
import com.timechaser.dto.CreateUserResponse;
import com.timechaser.dto.RoleDto;
import com.timechaser.dto.UserDto;
import com.timechaser.dto.UpdateUserDetailsRequest;
import com.timechaser.dto.UpdateUserPasswordRequest;
import com.timechaser.entity.User;
import com.timechaser.enums.UserRoles;
import com.timechaser.exception.CreateException;
import com.timechaser.exception.NotFoundException;
import com.timechaser.exception.UserUpdateDetailsException;
import com.timechaser.exception.UserUpdatePasswordException;
import com.timechaser.filter.JwtTokenFilter;
import com.timechaser.repository.UserRepository;
import com.timechaser.service.AuthorizationService;
import com.timechaser.service.UserService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = {UserRoles.ADMIN})
class UserControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private JwtTokenFilter jwtTokenFilter;
	
	@MockBean
	private AuthorizationService authorizationService;
	
	@Autowired
    private ObjectMapper objectMapper;

	private CreateUserRequest request;
	
    private AddRoleDto addRoleDto;
    private RoleDto roleDto;
	private UserDto userDto;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        request = new CreateUserRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setPassword("password");
        request.setUsername("username");

		userDto = new UserDto();
		userDto.setId(1L);
		userDto.setUsername("A");
		userDto.setFirstName("John");
		userDto.setLastName("Doe");
		userDto.setPto(3);

        addRoleDto = new AddRoleDto();
        addRoleDto.setRoleId(1L);
        
        roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("Admin");
        
		when(authorizationService.isAdminOrSelf(any())).thenReturn(true);
		when(authorizationService.isSelf(any())).thenReturn(true);

    }

	
	@Test
	void UserController_CreateUser_ReturnCreated() throws Exception{
		CreateUserResponse responseDto = new CreateUserResponse();
		responseDto.setFirstName("First");
		responseDto.setLastName("Last");
		responseDto.setUsername("username");
		responseDto.setPto(120);
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
	            .andExpect(MockMvcResultMatchers.jsonPath("$.pto", CoreMatchers.is(responseDto.getPto())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(responseDto.getUsername())));

	}
	
	@Test
	void UserController_CreateUser_UserCreationFailure() throws Exception{
		
		when(userService.create(any(CreateUserRequest.class))).thenThrow(new CreateException("Faiied to create user", new Exception()));
		
		ResultActions response = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isInternalServerError());

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
		
		ResultActions response = mockMvc.perform(put("/user/1/details")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	void UserController_Update_User_Details_500() throws Exception{
		
		doThrow(new UserUpdateDetailsException("testing123")).when(userService).updateDetails(anyLong(), any(UpdateUserDetailsRequest.class));
		
		ResultActions response = mockMvc.perform(put("/user/1/details")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}
	
	@Test
	void UserController_Update_User_Details_404() throws Exception{
		
		doThrow(new NotFoundException("testing123")).when(userService).updateDetails(anyLong(), any(UpdateUserDetailsRequest.class));
		
		ResultActions response = mockMvc.perform(put("/user/1/details")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	void UserController_Update_User_Details_NoFirstName() throws Exception{
		
		request.setFirstName(null);
		ResultActions response = mockMvc.perform(put("/user/1/details")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_Update_User_Details_NoLastName() throws Exception{
		
		request.setLastName(null);
		ResultActions response = mockMvc.perform(put("/user/1/details")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_Update_User_Password_Success() throws Exception {
		
		ResultActions response = mockMvc.perform(put("/user/1/password")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	void UserController_Update_User_Password_500() throws Exception{
		
		doThrow(new UserUpdatePasswordException("testing123")).when(userService).updatePassword(anyLong(), any(UpdateUserPasswordRequest.class));
		
		ResultActions response = mockMvc.perform(put("/user/1/password")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}
	
	@Test
	void UserController_Update_User_Password_404() throws Exception{
		
		doThrow(new NotFoundException("testing123")).when(userService).updatePassword(anyLong(), any(UpdateUserPasswordRequest.class));
		
		ResultActions response = mockMvc.perform(put("/user/1/password")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	void UserController_Update_User_Password_NoPassword() throws Exception{
		
		request.setPassword(null);
		ResultActions response = mockMvc.perform(put("/user/1/password")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(request)));
		
	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	void UserController_GetUser_Success() throws Exception {
	    User user = new User();
	    user.setPto(10);
	    user.setId(1L);
	    user.setUsername("username");
	    user.setFirstName("First");
	    user.setLastName("Last");
	    user.setPassword("password"); // This should be ignored in the JSON response

	    when(userService.findById(1L)).thenReturn(Optional.of(user));

	    ResultActions response = mockMvc.perform(get("/user/1")
	            .contentType(MediaType.APPLICATION_JSON));

	    response.andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(user.getId().intValue())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(user.getUsername())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.pto", CoreMatchers.is(user.getPto())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist());
	}


	@Test
	void UserController_GetUser_NotFound() throws Exception {
	    when(userService.findById(1L)).thenReturn(Optional.empty());

	    ResultActions response = mockMvc.perform(get("/user/1")
	            .contentType(MediaType.APPLICATION_JSON));

	    response.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
    void UserController_FindAllUsers_ReturnOk() throws Exception {
        List<UserDto> userDtos = Arrays.asList(userDto);
        when(userService.findAll()).thenReturn(userDtos);

        ResultActions response = mockMvc.perform(get("/user/all")
                .contentType(MediaType.APPLICATION_JSON));

		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDtos);
		System.out.println(json);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(userDto.getId().intValue())))
                .andExpect(jsonPath("$[0].username", CoreMatchers.is(userDto.getUsername())))
				.andExpect(jsonPath("$[0].firstName", CoreMatchers.is(userDto.getFirstName())))
				.andExpect(jsonPath("$[0].lastName", CoreMatchers.is(userDto.getLastName())))
				.andExpect(jsonPath("$[0].pto", CoreMatchers.is(userDto.getPto())));
    }
}
