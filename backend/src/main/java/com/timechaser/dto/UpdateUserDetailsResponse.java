package com.timechaser.dto;


import com.timechaser.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDetailsResponse {
	private String username;
	private String firstName;
	private String lastName;
	
	public UpdateUserDetailsResponse(User user) {
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}
}
