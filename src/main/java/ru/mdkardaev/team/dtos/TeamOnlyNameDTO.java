package ru.mdkardaev.team.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamOnlyNameDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", example = "name", required = true)
    private String name;
}
