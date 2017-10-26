package ru.mdkardaev.game.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "Game")
@Data
@Builder
@AllArgsConstructor
public class GameDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", example = "football", required = true)
    private String name;

    @ApiModelProperty(value = "Description", example = "table football 2x2")
    private String description;
}
