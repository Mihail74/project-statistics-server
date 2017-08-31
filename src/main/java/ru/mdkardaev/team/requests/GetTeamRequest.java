package ru.mdkardaev.team.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on team")
@Data
public class GetTeamRequest {
    @ApiModelProperty(value = "Include users", example = "false")
    private boolean includeUsers = false;
    @ApiModelProperty(value = "Include games", example = "false")
    private boolean includeGames = false;
}
