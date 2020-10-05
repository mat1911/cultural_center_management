package com.app.cultural_center_management.security.filters;

import com.app.cultural_center_management.dto.securityDto.AppError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            if (!request.getRequestURI().equals("/security/refresh-token")) {
                AppError appError = new AppError("User should be authenticated anew " +
                        "or his token should be refreshed", 401);
                String dataToSend = new ObjectMapper().writeValueAsString(appError);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(dataToSend);
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
