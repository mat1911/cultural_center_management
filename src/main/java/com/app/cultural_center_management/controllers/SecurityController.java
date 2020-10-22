package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.RefreshTokenDto;
import com.app.cultural_center_management.dto.securityDto.RegisterUserDto;
import com.app.cultural_center_management.dto.securityDto.TokensDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserPasswordDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.security.tokens.TokensService;
import com.app.cultural_center_management.service.ConfirmationTokenService;
import com.app.cultural_center_management.service.EmailService;
import com.app.cultural_center_management.service.SecurityService;
import com.app.cultural_center_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {

    private final SecurityService securityService;
    private final TokensService tokensService;
    private final EmailService emailService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long register(@RequestBody @Valid RegisterUserDto registerUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        Long registeredUserId =  securityService.register(registerUserDto);
        String token = confirmationTokenService.generateConfirmationToken();
        String activationLink = confirmationTokenService.createUrlWithToken(token, "/security");

        confirmationTokenService.addNewToken(registeredUserId, token);
        emailService.sendVerificationToken(registerUserDto.getEmail(), activationLink);

        return registeredUserId;
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public TokensDto refreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) {
        return tokensService.parseTokenFromRefreshToken(refreshTokenDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public void activateAccount(@RequestParam String token, HttpServletResponse httpServletResponse){
        Long userId = confirmationTokenService.getUserIdByToken(token);
        securityService.activateAccount(userId);
        httpServletResponse.setHeader("Location", "http://localhost:4200");
    }

    @PostMapping("/forgot")
    @ResponseStatus(HttpStatus.OK)
    public void prepareUserForPasswordChange(@RequestParam String userEmail){
        String token = confirmationTokenService.generateConfirmationToken();
        String activationLink = confirmationTokenService.createUrlWithTokenFront(token, "/remind-password");

        confirmationTokenService.addNewToken(userEmail, token);
        emailService.sendPasswordReminder(userEmail, activationLink);
    }

    @PatchMapping("/remind-password")
    @ResponseStatus(HttpStatus.OK)
    public Long changePassword(@RequestParam String token, @Valid @RequestBody UpdateUserPasswordDto updateUserPasswordDto,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        Long userId = confirmationTokenService.getUserIdByToken(token);
        userService.updatePassword(userId, updateUserPasswordDto);

        return userId;
    }
}
