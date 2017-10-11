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

    @ApiModelProperty(value = "team", required = true)
    private TeamDTO team;

    private List<InviteDTO> invitedUsers;
}
