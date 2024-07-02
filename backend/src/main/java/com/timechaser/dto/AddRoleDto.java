package com.timechaser.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleDto {
	@NotNull(message = "Role ID is a required field")
	private Long roleId;
}
