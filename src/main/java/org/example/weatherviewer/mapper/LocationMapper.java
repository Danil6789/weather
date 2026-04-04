package org.example.weatherviewer.mapper;

import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.entity.Location;
import org.example.weatherviewer.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    List<LocationDto> toDtoList(List<Location> locations);

    @Mapping(target = "user", ignore = true)
    Location toEntity(LocationDto locationDto);

    @AfterMapping
    default void setUser(@MappingTarget Location location, LocationDto locationDto){
        User user = new User();
        user.setId(locationDto.getUserId());
        location.setUser(user);
    }

}
