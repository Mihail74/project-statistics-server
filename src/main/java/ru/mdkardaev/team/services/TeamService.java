package ru.mdkardaev.team.services;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.exceptions.TeamAlreadyExist;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;


    @Autowired
    private InviteService inviteService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private DBExceptionUtils dbExceptionUtils;

    /**
     * return created team id
     */
    public Long create(CreateTeamRequest request, String leaderLogin) {
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

        try {
            team = teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            dbExceptionUtils
                    .conditionThrowNewException(e,
                                                SQLStates.UNIQUE_VIOLATION,
                                                () -> new TeamAlreadyExist(String.format(
                                                        "Team with name: [%s] already exist.",
                                                        request.getName())));
            log.error(e.getMessage(), e);
            throw e;
        }

        inviteService.inviteUsersToTeam(userRepository.findByIdIn(request.getMembersID()), team);

        return team.getId();
    }

    /**
     * returns teams when user with userLogin is member
     */
    public List<TeamDTO> getUserTeams(String userLogin, TeamFormingStatus formingStatus) {
        List<Team> teams;
        if (formingStatus == null) {
            teams = teamRepository.findByUsers_login(userLogin);
        } else {
            teams = teamRepository.findByUsers_loginAndFormingStatus(userLogin, formingStatus);
        }
        return teams.stream()
                .map(e -> conversionService.convert(e, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO getTeam(Long id) {
        Team team = teamRepository.findOne(id);
        return conversionService.convert(team, TeamDTO.class);
    }

    public void formTeam(Long id) {
        Team team = teamRepository.findOne(id);
        team.setFormingStatus(TeamFormingStatus.FORMED);
        teamRepository.save(team);
    }
}
