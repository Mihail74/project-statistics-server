package ru.mdkardaev.common.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response when error is catched
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    
    private List<ErrorInfo> errors;
}
