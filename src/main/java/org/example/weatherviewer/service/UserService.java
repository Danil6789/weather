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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserRegisterDto userRegisterDto){
        User user = userMapper.toEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(UserLoginDto userLoginDto){
        User user = userRepository.findByLogin(userLoginDto.getLogin())
                .orElseThrow(() -> new InvalidCredentialsException("Логин или пароль не совпадают"));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Логин или пароль не совпадают");
        }

        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
