package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.team.dtos.TeamDTO;

import java.util.List;

@ApiModel(value = "Team and invited users")
@Data
@AllArgsConstructor
public class TeamAndInvites {

    public TeamAndInvites(TeamDTO team) {
        this.team = team;
    }

    @ApiModelProperty(value = "team", required = true)
    private TeamDTO team;

    @ApiModelProperty(value = "Invited to team users")
    private List<InviteDTO> invitedUsers;
}
