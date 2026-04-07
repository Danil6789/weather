package org.example.weatherviewer.auth.user;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.auth.user.dto.UserRegisterDto;
import org.example.weatherviewer.auth.user.exception.InvalidCredentialsException;
import org.example.weatherviewer.auth.user.exception.UserNotFoundException;
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
            throw new InvalidCredentialsException("пароли не совпадает");
        }
    }
}