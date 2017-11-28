package ru.mdkardaev.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import ru.mdkardaev.common.responses.ErrorInfo;
import ru.mdkardaev.common.responses.ErrorResponse;
import ru.mdkardaev.security.exceptions.BadCredentialsException;

import java.util.*;

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

        List<ErrorInfo> errors = new ArrayList<>(ex.getBindingResult().getAllErrors().size());

        for (ObjectError bindingError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) bindingError;
            errors.add(new ErrorInfo(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    @ExceptionHandler(Exception.class)//TODO: поменять здесь на мой класс
    public ResponseEntity<?> handleException(Exception e) throws Exception {
        if (e instanceof InvalidParameterException) {
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
            String annotationReason = responseStatusAnnotation.reason();
            if (!StringUtils.isEmpty(annotationReason)) {
                reason = annotationReason;
            }
        }

        //TODO: сделать свой класс ошибок, который будет возвращать code + причину. Убрать аннотации и использовать i18n по месту бросания exception'a
        return ResponseEntity.status(httpStatus).body(reason);
    }


    /**
     * Handles db exceptions
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        Object resultBody = e.getMessage();
        if (e.getCause() instanceof ConstraintViolationException) {
            //return sqlState - detail message
            PSQLException cause = (PSQLException) e.getCause().getCause();
            resultBody = constructSingleErrorResponse(cause.getSQLState(), cause.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resultBody);
    }

    private ResponseEntity<?> handlerInvalidParameterException(InvalidParameterException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Pair.of(e.getParameter(), e.getMessage()));
    }

    private ErrorResponse constructSingleErrorResponse(Object code, String title) {
        return new ErrorResponse(Collections.singletonList(new ErrorInfo(String.valueOf(code), title)));

    }

}
