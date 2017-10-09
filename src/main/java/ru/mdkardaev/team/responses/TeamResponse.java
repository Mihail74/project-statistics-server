package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.team.dtos.TeamDTO;

@ApiModel(value = "Team container")
@Data
@AllArgsConstructor
public class TeamResponse {

    @ApiModelProperty(value = "team", required = true)
    private TeamDTO team;
}
