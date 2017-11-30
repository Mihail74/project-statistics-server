package ru.mdkardaev.exceptions.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mdkardaev.exceptions.ApiException;
import ru.mdkardaev.exceptions.InvalidParametersException;
import ru.mdkardaev.exceptions.constants.ErrorID;
import ru.mdkardaev.exceptions.responses.ErrorDescription;
import ru.mdkardaev.exceptions.responses.ErrorResponse;
import ru.mdkardaev.security.exceptions.BadCredentialsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * handles violations of the constraint of validation. Result contains map for each violations field and its details message.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<ErrorDescription> errors = new ArrayList<>(ex.getBindingResult().getAllErrors().size());

        for (ObjectError bindingError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) bindingError;
            errors.add(new ErrorDescription(ErrorID.INVALID_PARAMETER, fieldError.getField(),
                                            fieldError.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) throws Exception {
        return ResponseEntity.status(e.getHttpStatus()).body(constructSingleErrorResponse(e.getErrorDescription()));
    }

    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<?> handleInvalidParametersException(InvalidParametersException e) throws Exception {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getErrorDescriptions()));
    }

    /**
     * Handles db exceptions
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDescription errorDescription = new ErrorDescription(ErrorID.DATABASE_CONSTRAINT_VIOLATION,
                                                                 "Internal error");

        if (e.getCause() instanceof ConstraintViolationException) {
            PSQLException cause = (PSQLException) e.getCause().getCause();
            errorDescription = new ErrorDescription(ErrorID.DATABASE_CONSTRAINT_VIOLATION, cause.getSQLState(),
                                                    cause.getMessage());
            status = errorDescription.getId().getHttpStatus();

        } else {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.status(status).body(constructSingleErrorResponse(errorDescription));
    }

    private ErrorResponse constructSingleErrorResponse(ErrorDescription errorDescription) {
        return new ErrorResponse(Collections.singletonList(errorDescription));

    }
}
