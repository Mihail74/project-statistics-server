package ru.mdkardaev.invite.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.dtos.InviteDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class GetMyInvitesResponse {

    private List<InviteDTO> invites;
}
