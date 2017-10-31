package ru.mdkardaev.invite.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.user.dtos.UserDTO;

@ApiModel(value = "Invite in team")
@Data
@Builder
@AllArgsConstructor
public class InviteDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Invited users", required = true)
    private UserDTO user;

    @ApiModelProperty(value = "Team", example = "1", required = true)
    private TeamDTO team;

    @ApiModelProperty(value = "Invite status", required = true)
    private InviteStatus status;
}
