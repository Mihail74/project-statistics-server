package ru.mdkardaev.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User with the specified login already exist")
public class UserAlreadyExist extends RuntimeException {

    public UserAlreadyExist(String detailMessage){
        super(detailMessage);
    }
}
