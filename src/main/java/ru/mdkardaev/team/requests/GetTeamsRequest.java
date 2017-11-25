package ru.mdkardaev.team.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.mdkardaev.team.enums.TeamFormingStatus;

@ApiModel(value = "Request for list of team")
@Data
public class GetTeamsRequest {

    @ApiModelProperty("Game ID")
    private Long gameID;

    @ApiModelProperty("Team forming status")
    private TeamFormingStatus formingStatus;

    @ApiModelProperty("User with the specified ID must be the team member")
    private Long memberID;
}
