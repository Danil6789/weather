package org.example.weatherviewer.handler;

import jakarta.servlet.http.HttpServletRequest;
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
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        redirectAttributes.addFlashAttribute("status", 409);

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


    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("status", 404);
        return "error/404";
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentials(InvalidCredentialsException e,
                                           RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/auth/login";
    }

    @ExceptionHandler(SessionExpiredException.class)
    public String handleSessionExpired(SessionExpiredException e,
                                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Сессия истекла. Пожалуйста, войдите снова.");
        return "redirect:/auth/login";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception e, Model model) {
        log.error("Unexpected error occurred", e);
        e.printStackTrace();
        model.addAttribute("error", "Произошла внутренняя ошибка сервера");
        model.addAttribute("status", 500);
        return "error/500";
    }
}