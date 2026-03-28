package org.example.weatherviewer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.SessionDto;
import org.example.weatherviewer.dto.auth.UserDto;
import org.example.weatherviewer.dto.auth.UserLoginDto;
import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.entity.Session;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.mapper.SessionMapper;
import org.example.weatherviewer.mapper.UserMapper;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final UserMapper userMapper;
    private final SessionMapper sessionMapper;

    public UserDto register(UserRegisterDto userRegisterDto){
        User user = userService.createUser(userRegisterDto);
        return userMapper.toDto(user);
    }

    public SessionDto login(UserLoginDto userLoginDto){
        User user = userService.getUserByLogin(userLoginDto);
        Session session = sessionService.createSession(user);
        return sessionMapper.toDto(session);
    }

    public void logout(UUID sessionId) {
        sessionService.deleteSession(sessionId);
    }
}