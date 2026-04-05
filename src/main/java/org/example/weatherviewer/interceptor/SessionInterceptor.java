package org.example.weatherviewer.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.UserSessionDto;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.mapper.UserMapper;
import org.example.weatherviewer.service.auth.SessionService;
import org.example.weatherviewer.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;
    private final CookieUtil cookieUtil;
    private final UserMapper userMapper;

    @Value("${session.cookie.id}")
    private String COOKIE_NAME;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        System.out.println(">>> SessionInterceptor works for: " + uri);

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return true;
        }
        Arrays.stream(cookies)
                .filter(c -> COOKIE_NAME.equals(c.getName()))
                .findFirst()
                .ifPresent(cookie -> {
                    try {
                        UUID sessionId = UUID.fromString(cookie.getValue());
                        User user = sessionService.getUserBySessionId(sessionId);
                        UserSessionDto userSessionDto = userMapper.toUserSessionDto(user);
                        request.setAttribute("user", userSessionDto);
                    } catch (Exception e) {
                        cookieUtil.deleteSessionCookie(response);
                    }
                });

        if (uri.equals("/search")) {
            if (request.getAttribute("user") == null) {
                redirectToLogin(request, response);
                return false;
            }
        }

        return true;
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestURI = request.getRequestURI();
            String queryString = request.getQueryString();

            String redirectUrl = requestURI;
            if (queryString != null && !queryString.isEmpty()) {
                redirectUrl += "?" + queryString;
            }
            String encodedUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);
            response.sendRedirect("/auth/login?redirect=" + encodedUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
