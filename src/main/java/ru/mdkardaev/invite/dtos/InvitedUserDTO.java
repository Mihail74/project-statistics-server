package ru.mdkardaev.invite.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.user.dtos.UserDTO;

@Data
@AllArgsConstructor
public class InvitedUserDTO {

    private UserDTO user;
    private InviteStatus inviteStatus;
}
