package ru.mdkardaev.match.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {

    TIMESTAMP("timestamp");

    private String property;
}
