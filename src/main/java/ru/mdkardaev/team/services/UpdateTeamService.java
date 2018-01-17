package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.exceptions.ApiException;
import ru.mdkardaev.exceptions.NoAccessException;
import ru.mdkardaev.exceptions.constants.ErrorID;
import ru.mdkardaev.exceptions.responses.ErrorDescription;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.i18n.services.Messages;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.EditTeamRequest;

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
    private TeamCheckService teamCheckService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private Messages messages;

    /**
     * Check that user with userLogin is leader of team with specified id and change formingStatus to {@link TeamFormingStatus#FORMED}
     */
    @Transactional
    public TeamDTO formTeam(Long id, Long userID) {
        Team team = teamCheckService.checkAndGetTeam(id);

        checkFormPossible(team, userID);

        team.setFormingStatus(TeamFormingStatus.FORMED);
        team.setNumberOfWinMatches(0L);
        team.setNumberOfMatches(0L);

        inviteService.deleteInvitesInTeam(team.getId());
        return conversionService.convert(teamRepository.save(team), TeamDTO.class);
    }

    /**
     * Edit team according to specified request.
     */
    public TeamDTO editTeam(EditTeamRequest request, Long id, Long userID) {
        Team team = teamCheckService.checkAndGetTeam(id);

        checkUserIsLeaderOfTeam(userID, team);

        team.setName(request.getName());
        
        return conversionService.convert(teamRepository.save(team), TeamDTO.class);
    }

    private void checkFormPossible(Team team, Long userID) {
        checkUserIsLeaderOfTeam(userID, team);

        if (team.getUsers().size() != team.getGame().getMemberCountInTeam()) {
            throw new ApiException(new ErrorDescription(ErrorID.NOT_ENOUGH_MEMBER_TO_FORM_TEAM,
                    messages.getMessage("team.errors.notEnoughUser")));
        }
    }

    private void checkUserIsLeaderOfTeam(Long userID, Team team) {
        if (!team.getLeader().getId().equals(userID)) {
            throw new NoAccessException(messages.getMessage("team.errors.onlyLeaderCanDoThis"));
        }
    }
}
