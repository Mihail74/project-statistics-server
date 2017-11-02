package ru.mdkardaev.match.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.team.dtos.TeamOnlyNameDTO;

@ApiModel(value = "Team and their score in match")
@Value
public class TeamMatchScoreDTO {

    @ApiModelProperty(value = "Team", required = true)
    private TeamOnlyNameDTO team;

    @ApiModelProperty(value = "Score", required = true)
    private Long score;
}
