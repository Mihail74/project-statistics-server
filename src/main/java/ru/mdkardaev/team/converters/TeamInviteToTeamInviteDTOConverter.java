package ru.mdkardaev.team.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.team.dtos.TeamInviteDTO;
import ru.mdkardaev.team.entity.TeamInvite;

@Component
public class TeamInviteToTeamInviteDTOConverter implements Converter<TeamInvite, TeamInviteDTO> {

    @Override
    public TeamInviteDTO convert(TeamInvite invite) {
//        return TeamInviteDTO.builder().
        return null;
    }
}
