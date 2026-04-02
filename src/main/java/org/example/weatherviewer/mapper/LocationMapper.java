package org.example.weatherviewer.mapper;

import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.entity.Location;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    List<LocationDto> toDtoList(List<Location> locations);
}
