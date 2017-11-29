package ru.mdkardaev.exceptions.responses;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.mdkardaev.exceptions.constants.ErrorID;

/**
 * Error description
 */
@Value
@AllArgsConstructor
public class ErrorDescription {

    public ErrorDescription(ErrorID id, String detail) {
        this.id = id;
        this.detail = detail;
    }

    /**
     * Unique identifier for particular error
     */
    private ErrorID id;

    /**
     * (Optional). Parameter that create a error
     */
    @NonFinal
    private String parameter;

    /**
     * Human-readable explanation of error
     */
    private String detail;
}
