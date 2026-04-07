package org.example.weatherviewer.forecast.weather;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.forecast.weather.dto.GeocodingResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final WeatherService weatherService;

    @GetMapping("/search")
    public String searchLocations(@RequestParam("query") String query, Model model) throws IOException {
        List<GeocodingResponse> locations = weatherService.searchLocations(query);
        model.addAttribute("locations", locations);
        model.addAttribute("queryName", query);

        return "search";
    }
}