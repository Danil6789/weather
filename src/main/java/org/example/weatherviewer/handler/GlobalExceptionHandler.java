package org.example.weatherviewer.handler;
import org.example.weatherviewer.exception.InvalidCredentialsException;
import org.example.weatherviewer.exception.SessionExpiredException;
import org.example.weatherviewer.exception.UserAlreadyExistsException;
import org.example.weatherviewer.exception.UserNotFoundException;
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
        log.warn("Registration failed: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/register";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException e, Model model) {
        log.warn("User not found: {}", e.getMessage());
        model.addAttribute("error", e.getMessage());
        model.addAttribute("status", 404);
        return "error/404";
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentials(InvalidCredentialsException e,
                                           RedirectAttributes redirectAttributes) {
        log.warn("Invalid credentials: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(SessionExpiredException.class)
    public String handleSessionExpired(SessionExpiredException e,
                                       RedirectAttributes redirectAttributes) {
        log.warn("Session expired: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Сессия истекла. Пожалуйста, войдите снова.");
        return "redirect:/login";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception e, Model model) {
        log.error("Unexpected error occurred", e);  // ← это уже есть
        e.printStackTrace();  // ← ДОБАВЬ ЭТО! Ошибка напечатается в консоль Tomcat
        model.addAttribute("error", "Произошла внутренняя ошибка сервера");
        model.addAttribute("status", 500);
        return "error/500";
    }
}