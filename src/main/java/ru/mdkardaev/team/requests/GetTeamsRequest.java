package ru.mdkardaev.team.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.mdkardaev.team.enums.TeamFormingStatus;

@ApiModel(value = "Request for teams list")
@Data
public class GetTeamsRequest {

    @ApiModelProperty("Game ID")
    private Long gameID;

    @ApiModelProperty("Team forming status")
    private TeamFormingStatus formingStatus;
}
