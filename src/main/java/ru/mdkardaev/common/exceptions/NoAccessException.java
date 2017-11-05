package ru.mdkardaev.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throws when a resource is requested that is not accessed
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "No access")
public class NoAccessException extends RuntimeException {
}
