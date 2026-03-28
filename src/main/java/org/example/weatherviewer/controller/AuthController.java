package org.example.weatherviewer.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.SessionDto;
import org.example.weatherviewer.dto.auth.UserLoginDto;
import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.service.AuthService;
import org.example.weatherviewer.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }



    @PostMapping("/register")
    public String register(
            @Valid UserRegisterDto userRegisterDto, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "register";
        }
        authService.register(userRegisterDto);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
            @Valid UserLoginDto userLoginDto, BindingResult bindingResult,
            HttpServletResponse response)
    {
        if(bindingResult.hasErrors()){
            return "login";
        }
        SessionDto sessionDto = authService.login(userLoginDto);
        cookieUtil.setSessionCookie(response, sessionDto.getId().toString());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(
            @CookieValue(value = "SESSION_ID", required = false)
            String sessionId, HttpServletResponse response)
    {
        if(sessionId != null && !sessionId.isEmpty()){

            authService.logout(UUID.fromString(sessionId));
        }
        cookieUtil.deleteSessionCookie(response);
        return "redirect: login";
    }
}
