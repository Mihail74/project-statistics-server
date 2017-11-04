package ru.mdkardaev.common.controller;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.security.exceptions.BadCredentialsException;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> errorFieldCauses = new HashMap<>();

        for (ObjectError bindingError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) bindingError;
            errorFieldCauses.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(new ErrorInfo(errorFieldCauses));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) throws Exception {
        if (e instanceof DataIntegrityViolationException) {
            return handlerDataIntegrityViolationException((DataIntegrityViolationException) e);
        } else if (e instanceof InvalidParameterException) {
            return handlerInvalidParameterException((InvalidParameterException) e);
        }

        if (!(e instanceof BadCredentialsException)) {
            //Doesn't log incorrect input password or login
            log.error(e.getMessage(), e);
        }

        ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String reason = e.getMessage();

        if (responseStatusAnnotation != null) {
            httpStatus = responseStatusAnnotation.code();
            reason = responseStatusAnnotation.reason();
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(reason));
    }


    /**
     * Handles db exceptions
     */
    private ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        Object errorCause = e.getMessage();
        if (e.getCause() instanceof ConstraintViolationException) {
            //return sqlState - detail message
            PSQLException cause = (PSQLException) e.getCause().getCause();
            errorCause = Pair.of(cause.getSQLState(), cause.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(errorCause));
    }

    private ResponseEntity<?> handlerInvalidParameterException(InvalidParameterException e) {
        //TODO подумать, какой код ошибки должен быть в случаях, когда один из аргументов валиден, но не может быть принят из-за ограничений в системе
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Pair.of(e.getParameter(), e.getMessage()));
    }

    /**
     * Class for describe error
     */
    @Value
    private class ErrorInfo {

        private Object cause;
    }
}
