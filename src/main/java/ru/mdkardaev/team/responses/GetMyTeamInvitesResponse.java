package ru.mdkardaev.team.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.team.dtos.TeamInviteDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class GetMyTeamInvitesResponse {

    private List<TeamInviteDTO> invites;
}
