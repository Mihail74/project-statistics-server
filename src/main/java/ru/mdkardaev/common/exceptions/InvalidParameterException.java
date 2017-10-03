package ru.mdkardaev.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown when invalid parameter passed in request
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Invalid parameter[s]")
public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String detailMessage) {
        super(detailMessage);
    }
}
