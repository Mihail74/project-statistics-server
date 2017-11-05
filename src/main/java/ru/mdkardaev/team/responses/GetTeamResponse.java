package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.team.dtos.TeamDTO;

@ApiModel(value = "Team")
@Value
public class GetTeamResponse {

    @ApiModelProperty(value = "team", required = true)
    private TeamDTO team;
}
