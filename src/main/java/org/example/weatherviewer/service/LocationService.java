package org.example.weatherviewer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.mapper.LocationMapper;
import org.example.weatherviewer.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public List<LocationDto> getUserLocations(Long userId){
        return locationMapper.toDtoList(locationRepository.findLocationsByUserId(userId));
    }
}
