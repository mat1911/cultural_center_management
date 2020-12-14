package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    VerificationToken deleteByToken(String token);
    List<VerificationToken> findAllByExpiryDateBefore(LocalDate date);

}
