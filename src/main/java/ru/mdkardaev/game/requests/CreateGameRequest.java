package ru.mdkardaev.game.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on game creation")
@Data
public class CreateGameRequest {

    @ApiModelProperty(value = "game name", example = "Футбол", required = true)
    private String name;
    @ApiModelProperty(value = "score to win a match", example = "5", required = true)
    private Integer scoreToWin;
}
