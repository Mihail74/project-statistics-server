package ru.mdkardaev.team.services;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.exceptions.InvalidParametersException;
import ru.mdkardaev.exceptions.factory.ErrorDescriptionFactory;
import ru.mdkardaev.exceptions.responses.ErrorDescription;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.i18n.services.Messages;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that handles requests to team creation
 */
@Service
public class TeamCreationService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InviteService inviteService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private ErrorDescriptionFactory errorDescriptionFactory;
    @Autowired
    private Messages messages;

    /**
     * Create team with specified leader and create invites for members
     *
     * @return created team
     */
    @Transactional
    public TeamDTO createTeamAndInviteMembers(CreateTeamRequest request, Long leaderID) {
        checkRequest(request, leaderID);

        Team team = createTeam(request, leaderID);

        inviteService.inviteUsersToTeam(request.getMembersID(), team.getId());

        return conversionService.convert(team, TeamDTO.class);
    }

    private void checkRequest(CreateTeamRequest request, Long leaderID) {
        Game game = gameRepository.findOne(request.getGameID());
        if (game == null) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("gameID",
                                                 messages.getMessage("game.errors.notFound", request.getGameID()));
            throw new InvalidParametersException(error);
        }

        List<User> users = userRepository.findAll(request.getMembersID());
        int membersCountInTeam = users.size() + 1; //+1 leader

        if (users.size() != CollectionUtils.size(request.getMembersID())
                || request.getMembersID().contains(leaderID)) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("membersID",
                                                 messages.getMessage("team.errors.incorrectMembersCount"));
            throw new InvalidParametersException(error);
        } else if (game.getMemberCountInTeam().intValue() != membersCountInTeam) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("membersID",
                                                 messages.getMessage("team.errors.incorrectMembersInTeamCount"));
            throw new InvalidParametersException(error);

        }
    }

    private Team createTeam(CreateTeamRequest request, Long leaderID) {
        Game game = gameRepository.findOne(request.getGameID());

        User leader = userRepository.findOne(leaderID);

        Team team = Team.builder()
                .name(request.getName())
                .game(game)
                .leader(leader)
                .users(Sets.newHashSet(leader))
                .formingStatus(TeamFormingStatus.FORMING)
                .build();

        return teamRepository.save(team);
    }
}
