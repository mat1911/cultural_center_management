package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.ArticleRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRatingRepository extends JpaRepository<ArticleRating, Long> {
    Boolean existsByArticle_IdAndUser_Id(Long articleId, Long userId);
}
