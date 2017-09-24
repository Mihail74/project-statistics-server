package ru.mdkardaev.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Game with the specified name already exist")
public class GameAlreadyExist extends RuntimeException {

    public GameAlreadyExist(String detailMessage) {
        super(detailMessage);
    }
}
