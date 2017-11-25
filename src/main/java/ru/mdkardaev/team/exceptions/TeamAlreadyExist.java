package ru.mdkardaev.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Team with the specified name already exist")
public class TeamAlreadyExist extends RuntimeException {

    public TeamAlreadyExist(String detailMessage) {
        super(detailMessage);
    }
}
