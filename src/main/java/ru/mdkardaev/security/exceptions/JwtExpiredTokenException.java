package ru.mdkardaev.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad token")
public class JwtExpiredTokenException extends AuthenticationException {

    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
