package ru.mdkardaev.exceptions;

import lombok.Getter;
import ru.mdkardaev.exceptions.responses.ErrorDescription;

import java.util.Collections;
import java.util.List;

/**
 * Exception thrown when invalid many parameters are passed in request
 */
public class InvalidParametersException extends ApiException {

    @Getter
    private List<ErrorDescription> errorDescriptions;

    public InvalidParametersException(ErrorDescription errorDescription) {
        this(Collections.singletonList(errorDescription));
    }

    public InvalidParametersException(List<ErrorDescription> errorDescriptions) {
        super();
        this.errorDescriptions = errorDescriptions;
        setHttpStatus(errorDescriptions.get(0).getId().getHttpStatus());
    }
}
