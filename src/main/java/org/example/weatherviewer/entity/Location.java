package org.example.weatherviewer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name="longitude", nullable = false)
    private BigDecimal longitude;

}
