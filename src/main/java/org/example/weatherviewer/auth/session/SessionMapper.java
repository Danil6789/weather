package org.example.weatherviewer.auth.session;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionDto toDto(Session session);
}
