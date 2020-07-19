package com.app.cultural_center_management.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "competitions")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    private Set<Contestant> contestants;

    @ManyToMany(mappedBy = "competitions")
    private Set<User> voters = new HashSet<>();
}
