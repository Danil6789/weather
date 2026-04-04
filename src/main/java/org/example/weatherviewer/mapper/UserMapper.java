package org.example.weatherviewer.mapper;

import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.dto.auth.UserDto;
import org.example.weatherviewer.dto.auth.UserSessionDto;
import org.example.weatherviewer.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserRegisterDto request);

    UserDto toDto(User user);

    UserSessionDto toUserSessionDto(User user);
}
