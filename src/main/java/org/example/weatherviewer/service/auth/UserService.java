package org.example.weatherviewer.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.InvalidCredentialsException;
import org.example.weatherviewer.exception.UserNotFoundException;
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
    public User getUserByLogin(String login){
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Нет такого логина"));
    }

    public void checkPassword(String rawPassword, String encodedPassword){
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException("пароль не совпадает");
        }
    }

    @Transactional(readOnly = true) //TODO: ПО сути этот метод не нужен никогда
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
