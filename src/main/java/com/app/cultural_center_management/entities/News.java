package com.app.cultural_center_management.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "short_description", length = 1000)
    private String shortDescription;
    @Column(length = 1000)
    private String description;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "date_of_add")
    private LocalDate dateOfAdd;
}
