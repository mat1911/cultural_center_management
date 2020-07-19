package com.app.cultural_center_management.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double rate;
    @Column(name = "since_date")
    private LocalDate sinceDate;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(mappedBy = "userAnnouncements")
    private Set<User> participants = new HashSet<>();

}
