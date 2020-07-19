package com.app.cultural_center_management.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "comp_id")
    private Competition competition;

    @Column(name = "votes_number")
    private Long votesNumber;
}
