package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.RefreshTokenDto;
import com.app.cultural_center_management.dto.securityDto.RegisterUserDto;
import com.app.cultural_center_management.dto.securityDto.ResponseData;
import com.app.cultural_center_management.dto.securityDto.TokensDto;
import com.app.cultural_center_management.security.tokens.TokensService;
import com.app.cultural_center_management.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {

    private final SecurityService securityService;
    private final TokensService tokensService;

    @PostMapping("/register")
    public ResponseData<Long> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseData
                .<Long>builder()
                .data(securityService.register(registerUserDto))
                .build();
    }

    @PostMapping("/refresh-token")
    public ResponseData<TokensDto> refreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseData
                .<TokensDto>builder()
                .data(tokensService.parseTokenFromRefreshToken(refreshTokenDto))
                .build();
    }
}
