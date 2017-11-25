package ru.mdkardaev.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.mdkardaev.security.exceptions.JwtExpiredTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;

    @Autowired
    public TokenAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (e instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), new AuthErrorResponse("Invalid username or password"));
        } else if (e instanceof JwtExpiredTokenException) {
            mapper.writeValue(response.getWriter(), new AuthErrorResponse("Token has expired"));
        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
            mapper.writeValue(response.getWriter(), new AuthErrorResponse("Invalid Authorization header"));
        } else {
            mapper.writeValue(response.getWriter(), new AuthErrorResponse("Authentication failed"));
        }
    }

    @AllArgsConstructor
    @Data
    private class AuthErrorResponse {

        private String details;
    }
}
