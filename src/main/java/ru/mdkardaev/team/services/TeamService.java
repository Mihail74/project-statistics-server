package ru.mdkardaev.team.services;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.exceptions.TeamAlreadyExist;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.requests.GetTeamRequest;
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
    private TeamInviteService teamInviteService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private DBExceptionUtils dbExceptionUtils;

    /**
     * return created team id
     */
    @Transactional
    public Long create(CreateTeamRequest request, String leaderLogin) {
        Game game = gameRepository.findByName(request.getGameName());
        if (game == null) {
            throw new InvalidParameterException("Game with specified [name] doesn't exist");
        }

        Team team = Team.builder()
                .name(request.getName())
                .users(Sets.newHashSet(userRepository.findByLogin(leaderLogin)))
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

        teamInviteService.inviteUsersToTeam(userRepository.findByLoginIn(request.getMemberLogins()), team);

        return team.getId();
    }

    public List<TeamDTO> getTeams(GetTeamRequest request) {
        return teamRepository.findAll()
                .stream()
                .map(e -> conversionService.convert(e, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO getTeam(Long id) {
        Team team = teamRepository.findOne(id);
        return conversionService.convert(team, TeamDTO.class);
    }
}
