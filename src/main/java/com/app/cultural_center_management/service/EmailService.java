package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.usersDto.UserMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String culturalCenterEmail;

    private final JavaMailSender emailSender;

    public void sendEmail(String subject, String to, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);
    }

    public void sendUserMessage(UserMessageDto userMessageDto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(culturalCenterEmail);
        mailMessage.setSubject(userMessageDto.getSubject() + " - wiadomość od " + userMessageDto.getUserEmail());
        mailMessage.setText(userMessageDto.getMessage());

        emailSender.send(mailMessage);
    }

    public void sendVerificationToken(String email, String activationLink){
        String message = "Dziękujemy za rejestrację w Miejskim Domu Kultury. W celu aktywacji konta skopiuj i wklej " +
                " poniższy link: " + activationLink;
        String subject = "Potwierdź swój email - Miejski Dom Kultury";
        sendEmail(subject, email, message);
    }

    public void sendPasswordReminder(String email, String activationLink){
        String message = "W celu zresetwoania hasła na stronie Miejskiego Domu Kultury skopiuj i " +
                "wklej ponoższy link:" + activationLink;
        String subject = "Resetowanie hasła - Miejski Dom Kultury";
        sendEmail(subject, email, message);
    }
}
