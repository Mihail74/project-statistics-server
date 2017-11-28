package ru.mdkardaev.common.responses;

import lombok.Value;

/**
 * Error description
 */
@Value
public class ErrorInfo {

    private String code;
    private String title;
}
