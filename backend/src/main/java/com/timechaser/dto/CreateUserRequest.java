package com.timechaser.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	@NotBlank(message = "Username cannot be empty.")
	private String username;
	@NotBlank(message = "Password cannot be empty.")
	private String password;
	@NotBlank(message = "First Name cannot be empty.")
	private String firstName;
	@NotBlank(message = "Last Name cannot be empty.")
	private String lastName;
}
