    package org.example.weatherviewer.service;

    import org.example.weatherviewer.config.TestAppConfig;
    import org.example.weatherviewer.dto.auth.UserRegisterDto;
    import org.example.weatherviewer.entity.User;
    import org.example.weatherviewer.exception.InvalidCredentialsException;
    import org.example.weatherviewer.exception.UserAlreadyExistsException;
    import org.example.weatherviewer.exception.UserNotFoundException;
    import org.example.weatherviewer.repository.UserRepository;
    import org.example.weatherviewer.service.auth.UserService;
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
            UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            User user = userService.createUser(userDto);

            assertNotNull(user);
            assertNotNull(user.getId());
            assertEquals("spider-man", user.getLogin());
            assertTrue(passwordEncoder.matches("peter-parker" ,user.getPassword()));

            List<User> users = userRepository.findAll();
            assertEquals(1, users.size());
        }

        @Test
        @DisplayName("Проверяем, что при дубликате логина, выбрасывается исключение")
        void createUser_dublicateLogin_returnException(){
            UserRegisterDto userDto1 = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            UserRegisterDto userDto2 = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            userService.createUser(userDto1);

            assertThrows(UserAlreadyExistsException.class, () -> {
                userService.createUser(userDto2);
            });
        }

        @Test
        @DisplayName("Проверяем, что при корректных данных, пользователь возвращается")
        void getUserByLogin_validCredentials_returnUser(){
            UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            User user = userService.createUser(userDto);

            User foundUser = userService.getUserByLogin(userDto.getLogin());

            assertNotNull(foundUser);
            assertEquals(foundUser.getId(), user.getId());
            assertEquals(foundUser.getLogin(), user.getLogin());
        }

        @Test
        @DisplayName("Проверяем, что при несовпадении пароля при входе, возвращается ошибка")
        void getUserByLogin_invalidConfirmPassword_returnException(){
            UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            User user = userService.createUser(userDto);

            User foundUser = userService.getUserByLogin(userDto.getLogin());

            assertThrows(InvalidCredentialsException.class, () -> {
                userService.checkPassword(user.getPassword(), foundUser.getPassword());
            });
        }

        @Test
        @DisplayName("Проверяем, что, при несуществующем логине, возвращается ошибка UserNotFoundException")
        void getUserByLogin_notFoundLogin_returnException(){
            UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            userService.createUser(userDto);

            assertThrows(UserNotFoundException.class, () -> {
                userService.getUserByLogin("venom");
            });
        }
    }