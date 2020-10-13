package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT article FROM Article article " +
            "WHERE article.title LIKE %?1% AND article.isAccepted = true " +
            "ORDER BY article.sinceDate DESC")
    Page<Article> findAllFilteredAndAccepted(Pageable pageable, String keyword);

    Page<Article> findAllByIsAcceptedTrueOrderBySinceDateDesc(Pageable pageable);
    Page<Article> findAllByIsAcceptedFalseOrderBySinceDateDesc(Pageable pageable);
}
