package ru.mdkardaev.game.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@ApiModel(value = "Game")
@Value
@Builder
public class GameDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", example = "football", required = true)
    private String name;

    @ApiModelProperty(value = "Description", example = "table football 2x2")
    private String description;

    @ApiModelProperty(value = "Score to win", required = true)
    private Long scoreToWin;

    @ApiModelProperty(value = "Team count in match", required = true)
    private Long teamCountInMatch;

    @ApiModelProperty(value = "Member count in team", required = true)
    private Long memberCountInTeam;
}
