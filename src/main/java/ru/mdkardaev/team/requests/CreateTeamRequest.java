package ru.mdkardaev.team.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "Request on team creation")
@Data
public class CreateTeamRequest {
    @ApiModelProperty(value = "team name", example = "name", required = true)
    private String name;
    @ApiModelProperty(value = "list of member's id", example = "[1,2,3]", required = true)
    private List<Long> memberIDs;
    @ApiModelProperty(value = "list of game's id", example = "[1,2,3]", required = true)
    private List<Long> gamesIDs;
}
