package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.exceptions.NoAccessException;
import ru.mdkardaev.i18n.services.Messages;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.repository.TeamRepository;

/**
 * Service that handles requests to update team information
 */
@Service
public class UpdateTeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private InviteService inviteService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private TeamOwnerService teamOwnerService;
    @Autowired
    private TeamCheckService teamCheckService;
    @Autowired
    private Messages messages;

    /**
     * Check that user with userLogin is leader of team with specified id and change formingStatus to {@link TeamFormingStatus#FORMED}
     */
    @Transactional
    public TeamDTO formTeam(Long id, Long userID) {
        Team team = teamCheckService.checkAndGetTeam(id);

        if (!teamOwnerService.isLeaderTeam(userID, id)) {
            throw new NoAccessException(messages.getMessage("team.errors.onlyLeaderCanTeamForm"));
        }

        team.setFormingStatus(TeamFormingStatus.FORMED);
        team.setNumberOfWinMatches(0L);
        team.setNumberOfMatches(0L);

        inviteService.deleteInvitesInTeam(team.getId());
        return conversionService.convert(teamRepository.save(team), TeamDTO.class);
    }
}
