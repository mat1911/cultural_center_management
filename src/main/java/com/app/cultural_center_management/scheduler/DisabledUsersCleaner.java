package com.app.cultural_center_management.scheduler;

import com.app.cultural_center_management.repository.UserRepository;
import com.app.cultural_center_management.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Transactional
@RequiredArgsConstructor
public class DisabledUsersCleaner {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 24 * 3600 * 1000)
    public void cleanDatabaseFromInactiveUsers(){
        verificationTokenRepository.findAllByExpiryDateBefore(LocalDate.now())
                .forEach(token -> {
                    verificationTokenRepository.delete(token);
                    if(!token.getUser().getIsEnabled()){
                        token.getUser().getRoles().clear();
                        userRepository.delete(token.getUser());
                    }
                });
    }
}
