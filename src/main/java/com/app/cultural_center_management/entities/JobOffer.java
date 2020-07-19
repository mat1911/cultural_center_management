package com.app.cultural_center_management.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "job_offers")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "working_hours")
    private String workingHours;
    private String description;
    @ManyToMany(mappedBy = "chosenJobOffers")
    private Set<User> applicants = new HashSet<>();
}
