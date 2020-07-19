package com.app.cultural_center_management.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "spectacle_events")
public class SpectacleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp spectacle_timestamp;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "spectacle_id")
    private Spectacle spectacle;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

}
