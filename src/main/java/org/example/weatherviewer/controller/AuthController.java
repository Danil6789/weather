package org.example.weatherviewer.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.SessionDto;
import org.example.weatherviewer.dto.auth.UserLoginDto;
import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.service.auth.AuthService;
import org.example.weatherviewer.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String login(@RequestParam(name = "redirect", required = false) String redirect, Model model) {
        model.addAttribute("loginDto", new UserLoginDto());
        model.addAttribute("redirect", redirect);

        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        authService.register(userRegisterDto);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDto userLoginDto,
                        BindingResult bindingResult,
                        @RequestParam(name = "redirect", required = false) String redirect,
                        HttpServletResponse response, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("redirect", redirect);
            return "login";
        }

        SessionDto sessionDto = authService.login(userLoginDto);
        cookieUtil.setSessionCookie(response, sessionDto.getId().toString());

        if (redirect != null && !redirect.isEmpty()) {
            String decodedRedirect = java.net.URLDecoder.decode(redirect, StandardCharsets.UTF_8);
            log.info("Redirecting to: {}", decodedRedirect);
            return "redirect:" + decodedRedirect;
        }

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
        return "redirect:/auth/login";
    }
}
