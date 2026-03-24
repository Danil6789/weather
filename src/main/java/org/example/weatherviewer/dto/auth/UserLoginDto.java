package org.example.weatherviewer.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {
    @NotBlank
    @Size(min = 6, max = 40)
    private String login;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
