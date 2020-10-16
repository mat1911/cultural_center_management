package com.app.cultural_center_management.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
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
            name = "participants",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ann_id", referencedColumnName = "id"))
    @EqualsAndHashCode.Exclude
    private Set<Affair> userAffairs = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "votes",
            joinColumns = @JoinColumn(name = "voter_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comp_id", referencedColumnName = "id"))
    @EqualsAndHashCode.Exclude
    private Set<Competition> competitions = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Contestant> contestants;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<AffairRating> affairRatings;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<ArticleRating> articleRatings;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Application> applications;

    @OneToMany(mappedBy = "owner", fetch =  FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Affair> affairs = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch =  FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Article> articles = new HashSet<>();
}
