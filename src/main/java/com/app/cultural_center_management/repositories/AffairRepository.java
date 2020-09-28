package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Affair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffairRepository extends JpaRepository<Affair, Long> {
}
