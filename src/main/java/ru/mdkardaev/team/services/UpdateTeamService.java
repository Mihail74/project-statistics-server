package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.EntityNotFoundException;
import ru.mdkardaev.common.exceptions.NoAccessException;
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

    /**
     * Check that user with userLogin is leader of team with specified id and change formingStatus to {@link TeamFormingStatus#FORMED}
     */
    @Transactional
    public TeamDTO formTeam(Long id, String userLogin) {
        Team team = teamRepository.findOne(id);
        if (team == null) {
            throw new EntityNotFoundException();
        }
        if (!teamOwnerService.isLeaderTeam(userLogin, id)) {
            throw new NoAccessException();
        }

        team.setFormingStatus(TeamFormingStatus.FORMED);
        team.setNumberOfWinMatches(0L);
        team.setNumberOfMatches(0L);

        inviteService.deleteInvitesInTeam(team.getId());
        return conversionService.convert(teamRepository.save(team), TeamDTO.class);
    }
}
