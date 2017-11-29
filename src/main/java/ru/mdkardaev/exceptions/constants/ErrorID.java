package ru.mdkardaev.exceptions.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum with error codes
 */
public enum ErrorID {

    ENTITY_NOT_FOUND(1, HttpStatus.NOT_FOUND),
    INVALID_PARAMETER(2, HttpStatus.BAD_REQUEST),
    NO_ACCESS(3, HttpStatus.NOT_ACCEPTABLE),
    DATABASE_CONSTRAINT_VIOLATION(4, HttpStatus.CONFLICT);

    @Getter(onMethod = @__({@JsonValue}))
    private int id;
    @Getter
    private HttpStatus httpStatus;

    ErrorID(Integer id, HttpStatus httpStatus) {
        this.id = id;
        this.httpStatus = httpStatus;
    }
}
