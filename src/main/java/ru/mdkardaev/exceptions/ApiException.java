package ru.mdkardaev.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import ru.mdkardaev.exceptions.constants.ErrorID;
import ru.mdkardaev.exceptions.responses.ErrorDescription;

import java.util.Optional;

/**
 * Base exception in API
 */
@NoArgsConstructor
public class ApiException extends RuntimeException {

    @Getter
    private ErrorDescription errorDescription;
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private HttpStatus httpStatus;

    public ApiException(ErrorDescription errorDescription) {
        super();
        this.errorDescription = errorDescription;
        this.httpStatus = errorDescription.getId().getHttpStatus();
    }

}
