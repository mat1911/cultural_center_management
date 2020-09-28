package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestantRepository extends JpaRepository<Contestant, Long> {
}
