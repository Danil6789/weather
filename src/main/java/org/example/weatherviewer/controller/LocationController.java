package org.example.weatherviewer.controller;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.UserSessionDto;
import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.service.forecast.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public String addLocation(@RequestAttribute(name = "user") UserSessionDto user, @ModelAttribute LocationDto locationDto){
        System.out.println("=== ADD LOCATION ===");
        System.out.println("User ID: " + user.getId());
        System.out.println("Location DTO: " + locationDto);
        System.out.println("Name: " + locationDto.getName());
        System.out.println("Latitude: " + locationDto.getLatitude());
        System.out.println("Longitude: " + locationDto.getLongitude());

        if (locationDto.getLatitude() == null) {
            System.out.println("ERROR: Latitude is NULL!");
        }
        if (locationDto.getLongitude() == null) {
            System.out.println("ERROR: Longitude is NULL!");
        }

        locationDto.setUserId(user.getId());
        locationService.addLocation(locationDto);

        return "redirect:/";
    }
}
