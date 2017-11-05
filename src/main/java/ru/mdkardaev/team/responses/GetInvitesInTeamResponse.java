package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.invite.dtos.InviteDTO;

import java.util.List;

@ApiModel(value = "Team and invited users")
@Value
public class GetInvitesInTeamResponse {

    @ApiModelProperty(value = "Invites in team")
    private List<InviteDTO> invites;
}
