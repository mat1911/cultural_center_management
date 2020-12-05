package com.app.cultural_center_management.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "since_date")
    private LocalDate sinceDate;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "is_accepted")
    private Boolean isAccepted;
    private String title;
    @Column(length = 5000)
    private String content;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<ArticleRating> articleRatings;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @EqualsAndHashCode.Exclude
    private User author;

}
