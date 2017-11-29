package ru.mdkardaev.exceptions;

import ru.mdkardaev.exceptions.constants.ErrorID;
import ru.mdkardaev.exceptions.responses.ErrorDescription;

/**
 * Exception thrown when requested entity not found
 */
public class EntityNotFoundException extends ApiException {

    public EntityNotFoundException(String detail) {
        super(new ErrorDescription(ErrorID.ENTITY_NOT_FOUND, detail));
    }
}
