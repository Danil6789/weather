package org.example.weatherviewer.dto.auth;

import com.example.weather.annotation.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@PasswordMatch(
        passwordField = "password",
        confirmPasswordField = "confirmPassword",
        message = "Пароли не совпадают"
)
public class UserRegisterDto {
    @NotBlank
    @Size(min = 6, max = 40)
    private String login;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String confirmPassword;
}
