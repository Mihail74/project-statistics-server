package ru.mdkardaev.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when requestet entity not found
 */
//TODO: http-status поменять
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Entity doesn't exist")
public class EntityNotFoundException extends RuntimeException {
}
