package org.example.weatherviewer.service.forecast;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.entity.Location;
import org.example.weatherviewer.mapper.LocationMapper;
import org.example.weatherviewer.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional(readOnly = true)
    public List<LocationDto> getUserLocations(Long userId){
        return locationMapper.toDtoList(locationRepository.findLocationsByUserId(userId));
    }

    @Transactional
    public void addLocation(LocationDto locationDto){
        locationRepository.save(locationMapper.toEntity(locationDto));
    }
}
