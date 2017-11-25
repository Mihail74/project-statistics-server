package ru.mdkardaev.common.exceptions;

import lombok.Getter;

/**
 * Exception thrown when invalid parameter passed in request
 */
public class InvalidParameterException extends RuntimeException {

    @Getter
    private String parameter;

    public InvalidParameterException(String parameter, String detailMessage) {
        super(detailMessage);
        this.parameter = parameter;
    }
}
