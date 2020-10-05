package com.app.cultural_center_management.security.filters;

import com.app.cultural_center_management.security.tokens.TokensService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


public class JwtAuthorizationToken extends BasicAuthenticationFilter {

    private TokensService tokensService;

    public JwtAuthorizationToken(AuthenticationManager authenticationManager, TokensService tokensService) {
        super(authenticationManager);
        this.tokensService = tokensService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(accessToken)) {
            UsernamePasswordAuthenticationToken auth = tokensService.parseToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
