package ru.mdkardaev.match.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.team.dtos.TeamOnlyNameDTO;

import java.util.Set;

@ApiModel(value = "Match")
@Value
@Builder
public class MatchDTO {

    @ApiModelProperty(value = "ID", required = true)
    private Long id;

    @ApiModelProperty(value = "Teams and their score", required = true)
    private Set<TeamMatchScoreDTO> teamsMatchScore;

    @ApiModelProperty(value = "Winner team", required = true)
    private TeamOnlyNameDTO winnerTeam;

    @ApiModelProperty(value = "Game", required = true)
    private GameDTO game;

    @ApiModelProperty(value = "Match timestamp", required = true)
    private Long timestamp;
}
