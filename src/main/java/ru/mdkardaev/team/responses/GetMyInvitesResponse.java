package ru.mdkardaev.team.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.team.dtos.InviteDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class GetMyInvitesResponse {

    private List<InviteDTO> invites;
}
