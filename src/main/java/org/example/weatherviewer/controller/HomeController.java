package org.example.weatherviewer.controller;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.UserSessionDto;
import org.example.weatherviewer.dto.forecast.ForecastDto;
import org.example.weatherviewer.service.ForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final ForecastService forecastService;

    @GetMapping("/")
    public String home(@RequestAttribute(name = "user", required = false) UserSessionDto user, Model model){
        if(user != null){
            List<ForecastDto> forecasts = forecastService.getUserForecasts(user.getId());
            model.addAttribute("forecasts", forecasts);
        }

        return "home";
    }
}