package ru.mdkardaev.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Game must have name")
public class InvalidRequestParameter  extends RuntimeException{

    public InvalidRequestParameter(String detailMessage) {
        super(detailMessage);
    }
}
