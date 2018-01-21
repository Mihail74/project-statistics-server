package ru.mdkardaev.team.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {

    NAME("name");

    private String property;
}
