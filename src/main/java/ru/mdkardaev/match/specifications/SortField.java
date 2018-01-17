package ru.mdkardaev.match.specifications;

import lombok.Getter;

@Getter
public enum SortField {

    TIMESTAMP("timestamp");

    private String property;

    SortField(String property) {
        this.property = property;
    }
}
