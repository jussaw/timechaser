package com.timechaser.dto;


import com.timechaser.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	
	public CreateUserResponse(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}
}
