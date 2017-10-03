package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.team.dtos.TeamDTO;

@ApiModel(value = "Response on create team")
@Data
@AllArgsConstructor
public class CreateTeamResponse {

    @ApiModelProperty(value = "Created team", required = true)
    private TeamDTO team;
}
