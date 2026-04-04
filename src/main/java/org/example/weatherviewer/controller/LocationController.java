package org.example.weatherviewer.controller;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.UserSessionDto;
import org.example.weatherviewer.dto.location.LocationCreateDto;
import org.example.weatherviewer.service.forecast.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public String addLocation(@RequestAttribute(name = "user") UserSessionDto user, @ModelAttribute LocationCreateDto locationDto){
        locationService.addLocation(locationDto, user.getId());

        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteLocation(@RequestAttribute(name = "user") UserSessionDto user, @PathVariable(name = "id") Long id){
        locationService.deleteLocation(id, user.getId());

        return "redirect:/";
    }
}