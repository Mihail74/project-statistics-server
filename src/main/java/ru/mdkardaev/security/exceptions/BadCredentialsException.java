package ru.mdkardaev.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad credentials")
public class BadCredentialsException extends org.springframework.security.authentication.BadCredentialsException {

    public BadCredentialsException(String msg) {
        super(msg);
    }

    public BadCredentialsException(String msg, Throwable t) {
        super(msg, t);
    }
}
