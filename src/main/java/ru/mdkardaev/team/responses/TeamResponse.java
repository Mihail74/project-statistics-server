package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.dtos.InvitedUserDTO;
import ru.mdkardaev.team.dtos.TeamDTO;

import java.util.List;

@ApiModel(value = "Team container")
@Data
@AllArgsConstructor
public class TeamResponse {

    @ApiModelProperty(value = "team", required = true)
    private TeamDTO team;

    private List<InvitedUserDTO> invitedUsers;
}
