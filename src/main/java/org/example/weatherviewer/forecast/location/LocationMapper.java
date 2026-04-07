package org.example.weatherviewer.forecast.location;

import org.example.weatherviewer.forecast.location.dto.LocationCreateDto;
import org.example.weatherviewer.forecast.location.dto.LocationDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "userId", source = "user.id")
    LocationDto toDto(Location location);

    List<LocationDto> toDtoList(List<Location> locations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    Location toEntity(LocationCreateDto createDto, Long userId);

}
