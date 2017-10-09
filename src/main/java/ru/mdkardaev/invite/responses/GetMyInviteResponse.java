package ru.mdkardaev.invite.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.dtos.InviteDTO;

@Data
@AllArgsConstructor
public class GetMyInviteResponse {

    private InviteDTO invite;
}
