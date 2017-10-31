package ru.mdkardaev.game.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request on game creation")
@Data
public class CreateGameRequest {

    @ApiModelProperty(value = "Name", example = "football", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "Description", example = "table football 2x2")
    private String description;

    @ApiModelProperty(value = "Score to win", required = true)
    private Long scoreToWin;

    @ApiModelProperty(value = "Team count in match", required = true)
    private Long teamCountInMatch;
}
