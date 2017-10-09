package ru.mdkardaev.invite.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.user.dtos.UserDTO;

@ApiModel(value = "Invite to team")
@Data
@Builder
@AllArgsConstructor
public class InviteDTO {

    private Long id;

    private UserDTO user;

    private TeamDTO team;

    private InviteStatus status;
}
