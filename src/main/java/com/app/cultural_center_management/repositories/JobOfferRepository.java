package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
}
