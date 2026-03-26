package org.example.weatherviewer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.UserLoginDto;
import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.InvalidCredentialsException;
import org.example.weatherviewer.mapper.UserMapper;
import org.example.weatherviewer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserRegisterDto userDto){
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(UserLoginDto userDto){
        User user = userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new InvalidCredentialsException("Логин или пароль не совпадают"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Логин или пароль не совпадают");
        }

        return user;
    }
}
