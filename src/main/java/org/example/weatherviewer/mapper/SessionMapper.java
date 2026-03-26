package org.example.weatherviewer.mapper;

import org.example.weatherviewer.dto.auth.SessionDto;
import org.example.weatherviewer.entity.Session;
import org.mapstruct.Mapper;

@Mapper
public interface SessionMapper {
    SessionDto toDto(Session session);
}
