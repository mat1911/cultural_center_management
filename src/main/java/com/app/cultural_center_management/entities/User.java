package com.app.cultural_center_management.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private Integer age;
    private String email;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "phone_number")
    private String phoneNumber;
    private Boolean newsletter;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "applications",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
    private Set<JobOffer> chosenJobOffers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "participants",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ann_id", referencedColumnName = "id"))
    private Set<Announcement> userAnnouncements = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> userRoles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "votes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comp_id", referencedColumnName = "id"))
    private Set<Competition> competitions = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<SpectacleEvent> spectacleEvent;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Contestant> contestants;

    @OneToMany(mappedBy = "owner", fetch =  FetchType.EAGER)
    private Set<Announcement> announcements = new HashSet<>();
}
