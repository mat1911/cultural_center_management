package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Page<Competition> findAllByOrderBySinceDateDesc(Pageable pageable);
}
