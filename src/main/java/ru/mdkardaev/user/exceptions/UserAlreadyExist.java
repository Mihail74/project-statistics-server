package ru.mdkardaev.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with the specified email already exist")
public class UserAlreadyExist extends RuntimeException {

    public UserAlreadyExist(String detailMessage){
        super(detailMessage);
    }
}
