package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Affair;
import com.app.cultural_center_management.entities.AffairRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffairRatingRepository extends JpaRepository<AffairRating, Long> {
    Boolean existsByAffair_IdAndUser_Id(Long affairId, Long userId);
    AffairRating deleteByAffair(Affair affair);
}
