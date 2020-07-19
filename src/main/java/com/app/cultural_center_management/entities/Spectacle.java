package com.app.cultural_center_management.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "spectacles")
public class Spectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double rate;
    @Column(name = "poster_url")
    private String posterUrl;

    @OneToMany(mappedBy = "spectacle", fetch = FetchType.EAGER)
    private Set<SpectacleEvent> events = new HashSet<>();
}
