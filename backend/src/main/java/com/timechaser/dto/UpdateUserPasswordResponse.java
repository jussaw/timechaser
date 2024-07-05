package com.timechaser.dto;


import com.timechaser.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordResponse {
	private Long id;
	
	public UpdateUserPasswordResponse(User user) {
		this.id = user.getId();
	}
}
