package com.app.cultural_center_management.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "picture_url")
    private String  pictureUrl;
    @Column(name = "picture_title")
    private String pictureTitle;
}
