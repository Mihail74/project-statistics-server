package ru.mdkardaev.team.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.exceptions.TeamAlreadyExist;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.requests.GetTeamRequest;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private ConversionService conversionService;
    @Autowired
    private DBExceptionUtils dbExceptionUtils;

    public void create(CreateTeamRequest request) {
        Set<User> users = new HashSet<>(userRepository.findAll(request.getMemberIDs()));
        Set<Game> games = new HashSet<>(gameRepository.findAll(request.getGamesIDs()));

        Team team = Team.builder()
                        .name(request.getName())
                        .users(users)
                        .games(games)
                        .build();

        try {
            teamRepository.save(team);
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
    }

    public List<TeamDTO> getTeams(GetTeamRequest request) {
        return teamRepository.findAll()
                             .stream()
                             .map(e -> conversionService.convert(e, TeamDTO.class))
                             .collect(Collectors.toList());
    }
}
