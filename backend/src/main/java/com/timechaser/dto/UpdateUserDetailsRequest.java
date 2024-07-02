package com.timechaser.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDetailsRequest {
	@NotBlank(message = "First Name cannot be empty.")
	private String firstName;
	@NotBlank(message = "Last Name cannot be empty.")
	private String lastName;
}
