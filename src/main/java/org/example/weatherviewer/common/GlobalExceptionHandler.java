package org.example.weatherviewer.common;

import jakarta.servlet.http.HttpServletRequest;
import org.example.weatherviewer.auth.user.dto.UserLoginDto;
import org.example.weatherviewer.auth.user.exception.InvalidCredentialsException;
import org.example.weatherviewer.forecast.location.exception.LocationAlreadyExistsException;
import org.example.weatherviewer.auth.session.exception.SessionAlreadyExistsException;
import org.example.weatherviewer.auth.session.exception.SessionExpiredException;
import org.example.weatherviewer.auth.user.exception.UserAlreadyExistsException;
import org.example.weatherviewer.auth.user.exception.UserNotFoundException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiInterruptException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiNetworkException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiResponseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WeatherApiResponseException.class)
    public String handleWeatherApiResponse(WeatherApiResponseException e,
                                           RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorConn", "Weather service error. Please try again later");

        return "redirect:/search";
    }


    @ExceptionHandler(WeatherApiNetworkException.class)
    public String handleWeatherApiNetwork(WeatherApiNetworkException e,
                                   RedirectAttributes redirectAttributes,
                                          HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("errorConn", "Network error while connecting to OpenWeather API");

        return "redirect:/search";
    }

    @ExceptionHandler(WeatherApiInterruptException.class)
    public String handleWeatherApiInterrupt(WeatherApiInterruptException e,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorConn", "Server-side problems api");

        return "redirect:/search";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException e,
                                          RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "There is already a user with this login");

        return "redirect:/auth/register";
    }

    @ExceptionHandler(LocationAlreadyExistsException.class)
    public String handleLocationAlreadyExists(LocationAlreadyExistsException e,
                                              RedirectAttributes redirectAttributes,
                                              HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        String lat = request.getParameter("latitude");
        String lon = request.getParameter("longitude");

        if (lat != null && lon != null) {
            redirectAttributes.addFlashAttribute("errorLat", lat);
            redirectAttributes.addFlashAttribute("errorLon", lon);
        }

        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        }

        return "redirect:/search";
    }

    @ExceptionHandler({InvalidCredentialsException.class, UserNotFoundException.class})
    public String handleInvalidCredentialsOrUserNotFound(Exception e, Model model) {
        model.addAttribute("error", "The login or password does not match");
        model.addAttribute("userLoginDto", new UserLoginDto());

        return "login";
    }

    @ExceptionHandler(SessionExpiredException.class)
    public String handleSessionExpired(SessionExpiredException e,
                                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "An internal server error occurred");

        return "redirect:/auth/login";
    }

    @ExceptionHandler(SessionAlreadyExistsException.class)
    public String handleSessionAlreadyExistsException(Exception e, Model model) {
        model.addAttribute("error", "An internal server error occurred");

        return "error/409";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception e, Model model) {
        model.addAttribute("error", "An internal server error occurred");

        return "error/500";
    }
}