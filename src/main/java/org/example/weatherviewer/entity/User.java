package org.example.weatherviewer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false, length = 40)
    private String login;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Session> sessions;

}
