package org.example.weatherviewer.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.weatherviewer.validation.PasswordMatch;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatch
public class UserRegisterDto {
    @NotBlank
    @Size(min = 6, max = 40)
    private String login;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String confirmPassword;
}
