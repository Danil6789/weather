package org.example.weatherviewer.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSessionDto {
    private Long id;
    private String login;
}
