package org.example.weatherviewer.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weatherviewer.service.SessionService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class SessionFilter extends OncePerRequestFilter {
    private final SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/login") || path.startsWith("/register") ||
                path.startsWith("/css") || path.startsWith("/js") || path.equals("/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String sessionId = null;
        if (request.getCookies() != null) {
            sessionId = Arrays.stream(request.getCookies())
                    .filter(c -> "SESSION_ID".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (sessionId == null) {
            response.sendRedirect("/login");
            return;
        }

        try {
            // Валидируем сессию и кладем пользователя в request
            request.setAttribute("user",
                    sessionService.getUserBySessionId(UUID.fromString(sessionId)));
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendRedirect("/login?expired=true");
        }
    }
}