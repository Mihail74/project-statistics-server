package ru.mdkardaev.team.services;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

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

    /**
     * Create team with specified leader and create invites for members
     * @return created team
     */
    @Transactional
    public TeamDTO createTeamAndInviteMembers(CreateTeamRequest request, Long userID) {
        checkRequest(request);

        Team team = createTeam(request, userID);

        inviteService.inviteUsersToTeam(request.getMembersID(), team.getId());

        return conversionService.convert(team, TeamDTO.class);
    }

    private void checkRequest(CreateTeamRequest request) {
        Game game = gameRepository.findOne(request.getGameID());
        if (game == null) {
            throw new InvalidParameterException("gameID", String.format("Game with id = [%d] doesn't exist", request.getGameID()));
        }

        List<User> users = userRepository.findAll(request.getMembersID());
        if (users.size() != CollectionUtils.size(request.getMembersID())) {
            throw new InvalidParameterException("membersID", "Not all users with specified ids exist");
        }
    }

    private Team createTeam(CreateTeamRequest request, Long userID) {
        Game game = gameRepository.findOne(request.getGameID());

        User leader = userRepository.findOne(userID);

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
