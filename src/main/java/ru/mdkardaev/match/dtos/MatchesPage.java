package ru.mdkardaev.match.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.List;

@Value
public class MatchesPage {

    @ApiModelProperty("Content on this page")
    private List<MatchDTO> content;

    @ApiModelProperty("Total pages count")
    private Integer totalPages;

    @ApiModelProperty("Total elements count")
    private Long totalElements;

    @ApiModelProperty("Current page number")
    private Integer pageNumber;

    @ApiModelProperty("Count of elements on this page")
    private Integer countOfElements;
}
