package org.example.weatherviewer.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.example.weatherviewer.dto.auth.UserLoginDto;
import org.example.weatherviewer.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
        log.warn("error: " + e.getMessage());
        model.addAttribute("error", "The login or password does not match");
        model.addAttribute("loginDto", new UserLoginDto());
        return "login";  // ← Прямой ответ, без редиректа
    }

    @ExceptionHandler(SessionExpiredException.class)
    public String handleSessionExpired(SessionExpiredException e,
                                       RedirectAttributes redirectAttributes) {
        log.error("Unexpected error occurred", e);
        redirectAttributes.addFlashAttribute("error", "An internal server error occurred");
        return "redirect:/auth/login";
    }

    @ExceptionHandler(SessionAlreadyExistsException.class)
    public String handleSessionAlreadyExistsException(Exception e, Model model) {
        log.error("Unexpected error occurred", e);
        model.addAttribute("error", "An internal server error occurred");
        return "error/409";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception e, Model model) {
        log.error("Unexpected error occurred", e);
        e.printStackTrace();
        model.addAttribute("error", "An internal server error occurred");
        model.addAttribute("status", 500);
        return "error/500";
    }
}