package ru.mdkardaev.common.exceptions.utils;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import ru.mdkardaev.common.exceptions.sql.SQLStates;

import java.util.function.Supplier;

@Component
public class DBExceptionUtils {

    /**
     * В случае, причина возникновения Exception e является ConstraintViolationException с sqlState == conditionalSqlState,
     * то кидается исключение, поставляемое exceptionSupplier
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
