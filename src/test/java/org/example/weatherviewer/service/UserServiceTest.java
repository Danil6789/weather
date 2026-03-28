    package org.example.weatherviewer.service;

    import org.example.weatherviewer.config.TestAppConfig;
    import org.example.weatherviewer.dto.auth.UserRegisterDto;
    import org.example.weatherviewer.entity.User;
    import org.example.weatherviewer.repository.UserRepository;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.test.context.ActiveProfiles;
    import org.springframework.test.context.ContextConfiguration;
    import org.springframework.test.context.junit.jupiter.SpringExtension;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;

    @ActiveProfiles("test")
    @Transactional
    @ContextConfiguration(classes = {TestAppConfig.class})
    @ExtendWith(SpringExtension.class)
    class UserServiceTest {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private UserService userService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Test
        @DisplayName("Проверяет, что User создался в бд при корректных данных")
        void createUser_validCredentials_returnUser(){
            UserRegisterDto userDto = new UserRegisterDto();
            userDto.setLogin("spider-man");
            userDto.setPassword("peter-parker");
            userDto.setConfirmPassword("peter-parker");
            User user = userService.createUser(userDto);

            assertNotNull(user);
            assertNotNull(user.getId());
            assertEquals("spider-man", user.getLogin());
            assertTrue(passwordEncoder.matches("peter-parker" ,user.getPassword()));

            List<User> users = userRepository.findAll();
            assertEquals(1, users.size());
        }

    }
