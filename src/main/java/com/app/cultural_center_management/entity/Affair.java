package com.app.cultural_center_management.entity;

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
@Table(name = "affairs")
public class Affair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "short_description", length = 1000)
    private String shortDescription;
    @Column(name = "since_date")
    private LocalDate sinceDate;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "available_seats")
    private Long availableSeats;

    @OneToMany(mappedBy = "affair", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<AffairRating> affairRatings;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User owner;

    @ManyToMany(mappedBy = "userAffairs")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> participants = new HashSet<>();
}
