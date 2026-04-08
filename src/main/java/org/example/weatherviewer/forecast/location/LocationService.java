package org.example.weatherviewer.forecast.location;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.forecast.location.dto.LocationCreateDto;
import org.example.weatherviewer.forecast.location.dto.LocationDto;
import org.example.weatherviewer.forecast.location.exception.LocationNotFoundException;
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
    public void addLocation(LocationCreateDto locationDto, Long userId){
        locationRepository.save(locationMapper.toEntity(locationDto, userId));
    }

    @Transactional
    public void deleteLocation(Long id, Long userId){
        int line = locationRepository.deleteByIdAndUserId(id, userId);
        if(line == 0){
            throw new LocationNotFoundException("Локация не была найдена при попытке её удаления");
        }
    }
}