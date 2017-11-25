package ru.mdkardaev.game.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "Request on game creation")
@Data
public class CreateGameRequest {

    @ApiModelProperty(value = "Name", example = "name", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "Description")
    private String description;

    @ApiModelProperty(value = "Score to win", example = "5", required = true)
    @NotNull
    @Min(1)
    private Long scoreToWin;

    @ApiModelProperty(value = "Team count in match", example = "2", required = true)
    @NotNull
    @Min(2)
    private Long teamCountInMatch;

    @ApiModelProperty(value = "Member count in team", example = "2", required = true)
    @NotNull
    @Min(1)
    private Long memberCountInTeam;
}
