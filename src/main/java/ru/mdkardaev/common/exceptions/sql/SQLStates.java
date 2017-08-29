package ru.mdkardaev.common.exceptions.sql;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * SQL code error
 */
@Getter
public enum SQLStates {

    UNIQUE_VIOLATION(23505);

    private String code;

    SQLStates(int code) {
        this.code = String.valueOf(code);
    }

    public static SQLStates valueByCode(String code) {
        return Stream.of(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

}
