package ru.mdkardaev.invite.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class WrongInviteStatusException extends RuntimeException {

    public WrongInviteStatusException(String message) {
        super(message);
    }
}
