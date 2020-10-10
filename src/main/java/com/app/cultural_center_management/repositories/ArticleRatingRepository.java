package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.ArticleRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRatingRepository extends JpaRepository<ArticleRating, Long> {
    Boolean existsByArticle_IdAndUser_Id(Long articleId, Long userId);
}
