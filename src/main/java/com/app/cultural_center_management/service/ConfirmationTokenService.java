package com.app.cultural_center_management.service;


import com.app.cultural_center_management.entity.User;
import com.app.cultural_center_management.entity.VerificationToken;
import com.app.cultural_center_management.exception.ObjectNotFoundException;
import com.app.cultural_center_management.repository.UserRepository;
import com.app.cultural_center_management.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private String appUrl = "http://localhost:8080";
    private String frontAppUrl = "http://localhost:4200";

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public Long addNewToken(Long userId, String token){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id doesn't exist!"));
        return saveToken(user, token);
    }

    public Long addNewToken(String userEmail, String token){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id doesn't exist!"));
        return saveToken(user, token);
    }

    public String createUrlWithToken(String token, String endpoint){
        return appUrl + endpoint + "?token=" + token;
    }

    public String createUrlWithTokenFront(String token, String endpoint){
        return frontAppUrl + endpoint + "?token=" + token;
    }

    public String generateConfirmationToken(){
        return UUID.randomUUID().toString() + System.currentTimeMillis();
    }

    public Long getUserIdByToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException("Given token does not exist!"));

        if (verificationToken.getExpiryDate().isBefore(LocalDate.now())){
            throw new SecurityException("Token has expired!");
        }

        return verificationToken.getUser().getId();
    }

    private Long saveToken(User user, String token){
        LocalDate expirationDate = generateExpirationDate();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(expirationDate);
        return verificationTokenRepository.save(verificationToken).getId();
    }

    private LocalDate generateExpirationDate(){
        Long currentTimeMs = System.currentTimeMillis();
        Long dayMs = 1000L * 3600L * 24L;
        return Instant.ofEpochMilli(currentTimeMs + dayMs).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
