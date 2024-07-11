package com.timechaser.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private CreateUserResponse user;
	private String token;

}
