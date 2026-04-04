package org.example.weatherviewer.mapper;

import org.example.weatherviewer.dto.location.LocationCreateDto;
import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.entity.Location;
import org.example.weatherviewer.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "userId", source = "user.id")
    LocationDto toDto(Location location);

    List<LocationDto> toDtoList(List<Location> locations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")  // ← прямо указываем
    Location toEntity(LocationCreateDto createDto, Long userId);

}
