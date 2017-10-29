package ru.mdkardaev.team.services;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.responses.TeamAndInvites;
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
     */
    @Transactional
    public TeamAndInvites createTeamAndInviteMembers(CreateTeamRequest request, String leaderLogin) {
        Team team = createTeam(request, leaderLogin);
        List<InviteDTO> invitesDTO = inviteService.inviteUsersToTeam(request.getMembersID(), team.getId());

        return new TeamAndInvites(conversionService.convert(team, TeamDTO.class), invitesDTO);
    }

    private Team createTeam(CreateTeamRequest request, String leaderLogin) {
        Game game = gameRepository.findOne(request.getGameID());
        if (game == null) {
            throw new InvalidParameterException("Game with specified [name] doesn't exist");
        }

        User leader = userRepository.findByLogin(leaderLogin);
        Team team = Team.builder()
                        .name(request.getName())
                        .users(Sets.newHashSet(leader))
                        .leader(leader)
                        .game(game)
                        .formingStatus(TeamFormingStatus.FORMING)
                        .build();

        return teamRepository.save(team);
    }
}
