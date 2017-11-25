package ru.mdkardaev.common.exceptions.utils;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import ru.mdkardaev.common.exceptions.enums.SQLStates;

import java.util.function.Supplier;

@Component
public class DBExceptionUtils {

    /**
     * In case when cause of Exception is ConstraintViolationException with sqlState == conditionalSqlState,
     * then throws exception supplied by the exceptionSupplier
     */
    public void conditionThrowNewException(Exception e, SQLStates conditionalSqlState, Supplier<RuntimeException> exceptionSupplier) {
        if (e.getCause() instanceof ConstraintViolationException) {
            String sqlState = ((ConstraintViolationException) e.getCause()).getSQLException().getSQLState();
            if (conditionalSqlState == SQLStates.valueByCode(sqlState)) {
                throw exceptionSupplier.get();
            }
        }
    }
}
