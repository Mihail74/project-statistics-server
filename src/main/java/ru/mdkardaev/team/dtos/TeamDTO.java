package ru.mdkardaev.team.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.List;

@ApiModel(value = "Team")
@Value
@Builder
public class TeamDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", example = "football", required = true)
    private String name;

    @ApiModelProperty(value = "Team members", required = true)
    private List<UserDTO> users;

    @ApiModelProperty(value = "Leader", required = true)
    private UserDTO leader;

    @ApiModelProperty(value = "Game", example = "football", required = true)
    private GameDTO game;

    @ApiModelProperty(value = "Forming status", required = true)
    private TeamFormingStatus formingStatus;

    @ApiModelProperty(value = "Total number of matches", required = true)
    private Long numberOfMatches;

    @ApiModelProperty(value = "Total number of won matches", required = true)
    private Long numberOfWinMatches;
}
