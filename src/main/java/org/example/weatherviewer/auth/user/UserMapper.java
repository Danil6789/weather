package org.example.weatherviewer.auth.user;

import org.example.weatherviewer.auth.user.dto.UserRegisterDto;
import org.example.weatherviewer.auth.user.dto.UserDto;
import org.example.weatherviewer.auth.user.dto.UserSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserRegisterDto request);

    UserDto toDto(User user);

    UserSessionDto toUserSessionDto(User user);
}
