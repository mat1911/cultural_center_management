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
@Table(name = "competitions")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 3000)
    private String description;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "since_date")
    private LocalDate sinceDate;
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Contestant> contestants;

    @ManyToMany(mappedBy = "competitions")
    @EqualsAndHashCode.Exclude
    private Set<User> voters = new HashSet<>();
}
