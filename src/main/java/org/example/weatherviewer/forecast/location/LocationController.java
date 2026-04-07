package org.example.weatherviewer.forecast.location;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.auth.user.dto.UserSessionDto;
import org.example.weatherviewer.forecast.location.dto.LocationCreateDto;
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