package org.example.weatherviewer.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSessionDto {
    private Long id;
    private String login;
}
