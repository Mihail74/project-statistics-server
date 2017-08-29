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

    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;
    @ApiModelProperty(value = "name", example = "football", required = true)
    private String name;
    @ApiModelProperty(value = "scoreToWin", example = "5", required = true)
    private Integer scoreToWin;
}
