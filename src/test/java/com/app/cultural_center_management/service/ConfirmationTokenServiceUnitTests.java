package com.app.cultural_center_management.service;

import com.app.cultural_center_management.entity.User;
import com.app.cultural_center_management.entity.VerificationToken;
import com.app.cultural_center_management.repository.UserRepository;
import com.app.cultural_center_management.repository.VerificationTokenRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ConfirmationTokenServiceUnitTests {

    private final String APP_URL = "http://localhost:8080";
    private final String FRONT_APP_URL = "http://localhost:4200";

    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private UserRepository userRepository;

    private ConfirmationTokenService confirmationTokenService;

    @Test
    public void generatesTokenExpirationDate() {
        User user = User.builder().id(1L).build();
        ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor.forClass(VerificationToken.class);

        Mockito
                .when(verificationTokenRepository.save(verificationTokenArgumentCaptor.capture()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        confirmationTokenService = new ConfirmationTokenService(verificationTokenRepository, userRepository);
        Long tokenId = confirmationTokenService.addNewToken(1L, confirmationTokenService.generateConfirmationToken());

        LocalDate expirationDate = verificationTokenArgumentCaptor.getValue().getExpiryDate();

        Assertions.assertEquals(expirationDate, LocalDate.now().plusDays(1));
    }

    @Test
    public void returnUrlWithToken() {
        String endpoint = "/security";
        confirmationTokenService = new ConfirmationTokenService(verificationTokenRepository, userRepository);

        String token = confirmationTokenService.generateConfirmationToken();
        String url = confirmationTokenService.createUrlWithToken(token, endpoint);

        Assertions.assertEquals(url, APP_URL + endpoint + "?token=" + token);
    }

    @Test
    public void returnClientUrlWithToken() {
        String endpoint = "/security";
        confirmationTokenService = new ConfirmationTokenService(verificationTokenRepository, userRepository);

        String token = confirmationTokenService.generateConfirmationToken();
        String url = confirmationTokenService.createUrlWithTokenFront(token, endpoint);

        Assertions.assertEquals(url, FRONT_APP_URL + endpoint + "?token=" + token);
    }

    @Test
    public void confirmationTokenIsProperlyGenerated() {
        confirmationTokenService = new ConfirmationTokenService(verificationTokenRepository, userRepository);
        String token = confirmationTokenService.generateConfirmationToken();

        Assertions.assertTrue(token.toLowerCase().matches("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{25}"));
    }

    @Test
    public void throwsSecurityExceptionOnExpiredToken() {

        VerificationToken expiredToken = VerificationToken.builder()
                .expiryDate(LocalDate.now().minusDays(1))
                .build();

        Mockito
                .when(verificationTokenRepository.findByToken(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(expiredToken));

        confirmationTokenService = new ConfirmationTokenService(verificationTokenRepository, userRepository);

        Assertions.assertThrows(SecurityException.class, () -> confirmationTokenService.getUserIdByToken(ArgumentMatchers.anyString()));
    }
}
