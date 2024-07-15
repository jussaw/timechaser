package com.timechaser.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private CreateUserResponse user;
	private List<RoleDto> roles;
}
