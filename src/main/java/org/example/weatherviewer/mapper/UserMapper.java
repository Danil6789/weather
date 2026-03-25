package org.example.weatherviewer.mapper;

import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.dto.auth.UserResponse;
import org.example.weatherviewer.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toEntity(UserRegisterDto request);

    UserResponse toDto(User user);
}
