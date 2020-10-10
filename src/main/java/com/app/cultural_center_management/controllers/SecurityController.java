package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.RefreshTokenDto;
import com.app.cultural_center_management.dto.securityDto.RegisterUserDto;
import com.app.cultural_center_management.dto.securityDto.TokensDto;
import com.app.cultural_center_management.security.tokens.TokensService;
import com.app.cultural_center_management.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {

    private final SecurityService securityService;
    private final TokensService tokensService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long register(@RequestBody RegisterUserDto registerUserDto) {
        return securityService.register(registerUserDto);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public TokensDto refreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) {
        return tokensService.parseTokenFromRefreshToken(refreshTokenDto);
    }
}
