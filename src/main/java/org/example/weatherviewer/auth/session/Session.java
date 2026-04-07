package org.example.weatherviewer.auth.session;

import jakarta.persistence.*;
import lombok.Data;
import org.example.weatherviewer.auth.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
}