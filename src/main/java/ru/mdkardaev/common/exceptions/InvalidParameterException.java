package ru.mdkardaev.common.exceptions;

import lombok.Getter;

/**
 * Exception is thrown when invalid parameter passed in request
 */
public class InvalidParameterException extends RuntimeException {

    @Getter
    private String parameter;

    public InvalidParameterException(String parameter, String detailMessage) {
        super(detailMessage);
        this.parameter = parameter;
    }

    @Deprecated
    public InvalidParameterException( String detailMessage) {

        //TODO Удалить этот конструктор
        super(detailMessage);
    }
}
