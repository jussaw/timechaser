package com.timechaser.mapper;

import com.timechaser.dto.RoleDto;
import com.timechaser.entity.Role;

public class RoleMapper {
	public static Role toEntity(RoleDto roleDto) {
        return Role.builder()
            .id(roleDto.getId())
            .name(roleDto.getName())
            .build();
    }

    public static RoleDto toDto(Role role) {
        return RoleDto.builder()
            .id(role.getId())
            .name(role.getName())
            .build();
    }
}
