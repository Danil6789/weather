package org.example.weatherviewer.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.service.SessionService;
import org.example.weatherviewer.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;
    private final CookieUtil cookieUtil;

    @Value("${session.cookie.id}")
    private String COOKIE_NAME;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
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
                        request.setAttribute("user", user);
                    } catch (Exception e) {
                        cookieUtil.deleteSessionCookie(response);
                    }
                });

        return true;
    }
}
