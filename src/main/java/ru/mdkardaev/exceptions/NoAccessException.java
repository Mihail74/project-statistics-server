package ru.mdkardaev.exceptions;

import ru.mdkardaev.exceptions.constants.ErrorID;
import ru.mdkardaev.exceptions.responses.ErrorDescription;

/**
 * Exception throws when a resource is requested that is not accessed
 */
public class NoAccessException extends ApiException {

    public NoAccessException(String detail) {
        super(new ErrorDescription(ErrorID.NO_ACCESS, detail));
    }
}
