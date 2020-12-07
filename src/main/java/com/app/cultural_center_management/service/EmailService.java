package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.usersDto.UserMessageDto;
import com.app.cultural_center_management.exception.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String culturalCenterEmail;

    private final JavaMailSender emailSender;

    public void sendEmail(String subject, String to, String message, boolean isHtml) {
        try {
            MimeMessage mailMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, isHtml);

            emailSender.send(mailMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new EmailException("Internal server error occurred!");
        }
    }

    @Async
    public void sendUserMessage(UserMessageDto userMessageDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(culturalCenterEmail);
        mailMessage.setSubject(userMessageDto.getSubject() + " - wiadomość od " + userMessageDto.getUserEmail());
        mailMessage.setText(userMessageDto.getMessage());

        emailSender.send(mailMessage);
    }

    @Async
    public void sendVerificationToken(String email, String activationLink) {
        String header = "MIEJSKI OŚRODEK KULTURY - REJESTRACJA";
        String message = "Otrzymałeś tę wiadomość, ponieważ dla danego adresu email zostało założone konto" +
                " na stronie MDK. Kliknij w poniższy link aby je aktywować.";
        String buttonString = "AKTYWUJ KONTO";

        String subject = "Potwierdź swój email - Miejski Dom Kultury";
        String htmlMessage = createHtmlMessageFromTemplate(header, message, buttonString, activationLink);
        sendEmail(subject, email, htmlMessage, true);
    }

    @Async
    public void sendPasswordReminder(String email, String activationLink) {
        String header = "MIEJSKI OŚRODEK KULTURY - PRZYPOMNIENIE HASŁA";
        String message = "Otrzymałeś tę wiadomość, ponieważ dla danego adresu email wysłano prośbę o zmianę hasła " +
                " na stronie MDK. Kliknij w poniższy link aby zresetować hasło.";
        String buttonString = "RESETUJ HASŁO";

        String subject = "Resetowanie hasła - Miejski Dom Kultury";
        String htmlMessage = createHtmlMessageFromTemplate(header, message, buttonString, activationLink);
        sendEmail(subject, email, htmlMessage, true);
    }

    @Async
    public void sendAffairConfirmation(String email, String affairName){
        String appLink = "http://localhost:4200";
        String header = "MIEJSKI OŚRODEK KULTURY - ZAPISAŁEŚ SIĘ NA WYDARZENIE";
        String message = "Otrzymałeś tę wiadomość, ponieważ użytkownik zarejestrowany na stronie MDK, za pomocą " +
                "danego adres email został zapisany na wydarzenie " + affairName + ". Zarządzaj swoimi zapisami poprzez " +
                "zakładkę Wydarzenia.";

        String buttonString = "STRONA MDK";

        String subject = "Potwierdzenie zapisu na wydarzenie - Miejski Dom Kultury";
        String htmlMessage = createHtmlMessageFromTemplate(header, message, buttonString, appLink);
        sendEmail(subject, email, htmlMessage, true);
    }

    @Async
    public void sendArticleAcceptanceStatus(String email, String articleName, boolean articleStatus){
        String appLink = "http://localhost:4200";
        String header = "MIEJSKI OŚRODEK KULTURY - ZMIANA STATUSU ARTYKUŁU";
        String message = "Otrzymałeś tę wiadomość, ponieważ Twój artykuł - " + articleName + " " +
                "został sprawdzony przez administratora, a jego status został zmieniony.\n Artykuł zaakceptowany: " + articleStatus +
                "\nW celu uzyskania dodatkowych informacji skontaktuj się z przedstawicielami MDK.";

        String buttonString = "STRONA MDK";

        String subject = "Zmiana statusu artykułu - Miejski Dom Kultury";
        String htmlMessage = createHtmlMessageFromTemplate(header, message, buttonString, appLink);
        sendEmail(subject, email, htmlMessage, true);
    }

    private String createHtmlMessageFromTemplate(String header, String message, String buttonString, String buttonLink) {
        return """
                <html>
                    <body>
                        <div style="margin: auto; background-color: #343a40; height: 100%%">
                            <div style="background-color: white; width: 50%%; min-width: 210px; height: 100%%; margin: auto; padding: 10px;">
                                 <h2 style="text-align: center">%s</h2>
                                <p style="text-align: center; margin: 20px; margin-top: 50px; margin-bottom: 50px;">%s</p>
                                <div style="background-color: #007bff; width: 200px; border-radius: 10px; margin: auto; margin-bottom: 50px;">
                                    <a href="%s">
                                        <p style="color: white; font-weight: bold; text-align: center; vertical-align: middle; padding: 10px">%s</p>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </body>
                </html>
                 """.formatted(header, message, buttonLink, buttonString);
    }
}
