package com.app.cultural_center_management.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "result_url")
    private String resultUrl;

    @Column(name = "user_comment", length = 3000)
    private String userComment;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "comp_id")
    @EqualsAndHashCode.Exclude
    private Competition competition;

    @Column(name = "votes_number")
    private Long votesNumber;
}
