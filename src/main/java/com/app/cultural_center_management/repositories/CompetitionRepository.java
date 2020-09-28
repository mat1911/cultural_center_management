package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
