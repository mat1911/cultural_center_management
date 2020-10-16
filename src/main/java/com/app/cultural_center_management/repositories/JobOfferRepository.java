package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    @Query("SELECT jobOffer FROM JobOffer jobOffer " +
            "WHERE jobOffer.name LIKE %?1% ORDER BY jobOffer.sinceDate DESC")
    Page<JobOffer> findAllFilteredAndAccepted(Pageable pageable, String keyword);

    Page<JobOffer> findAllByOrderBySinceDateDesc(Pageable pageable);

}
