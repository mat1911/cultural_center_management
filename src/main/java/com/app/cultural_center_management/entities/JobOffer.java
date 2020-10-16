package com.app.cultural_center_management.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_offers")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "working_hours")
    private String workingHours;
    @Column(name = "since_date")
    private LocalDate sinceDate;
    @Column(length = 5000)
    private String description;

    @OneToMany(mappedBy = "jobOffer", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Application> applications;
}
