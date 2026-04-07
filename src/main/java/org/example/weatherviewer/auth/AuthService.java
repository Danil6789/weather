package org.example.weatherviewer.auth;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.auth.session.SessionDto;
import org.example.weatherviewer.auth.user.dto.UserDto;
import org.example.weatherviewer.auth.user.dto.UserLoginDto;
import org.example.weatherviewer.auth.user.dto.UserRegisterDto;
import org.example.weatherviewer.auth.session.Session;
import org.example.weatherviewer.auth.user.User;
import org.example.weatherviewer.auth.session.SessionMapper;
import org.example.weatherviewer.auth.user.UserMapper;
import org.example.weatherviewer.auth.session.SessionService;
import org.example.weatherviewer.auth.user.UserService;
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
        User user = userService.getUserByLogin(userLoginDto.getLogin());
        userService.checkPassword(userLoginDto.getPassword(), user.getPassword());
        Session session = sessionService.createSession(user);
        return sessionMapper.toDto(session);
    }

    public void logout(UUID sessionId) {
        sessionService.deleteSession(sessionId);
    }
}