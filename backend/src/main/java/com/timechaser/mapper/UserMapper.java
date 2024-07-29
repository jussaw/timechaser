package com.timechaser.mapper;

import com.timechaser.dto.UserDto;
import com.timechaser.entity.User;

public class UserMapper {
	public static User toEntity(UserDto userDto) {
        return User.builder()
            .id(userDto.getId())
            .username(userDto.getUsername())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .password(userDto.getPassword())
            .pto(userDto.getPto())
            .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
    		.id(user.getId())
            .username(user.getUsername())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .password(user.getPassword())
            .pto(user.getPto())
            .build();
    }
}
