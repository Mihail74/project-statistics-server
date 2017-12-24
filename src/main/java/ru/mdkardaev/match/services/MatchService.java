package ru.mdkardaev.match.services;

import lombok.extern.slf4j.Slf4j;
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
import ru.mdkardaev.match.dtos.MatchDTO;
import ru.mdkardaev.match.entity.Match;
import ru.mdkardaev.match.entity.TeamMatchScore;
import ru.mdkardaev.match.entity.TeamMatchScorePK;
import ru.mdkardaev.match.repository.MatchRepository;
import ru.mdkardaev.match.repository.TeamMatchScoreRepository;
import ru.mdkardaev.match.requests.CreateMatchRequest;
import ru.mdkardaev.match.requests.TeamScore;
import ru.mdkardaev.match.specifications.MatchSpecifications;
import ru.mdkardaev.match.specifications.MatchesFilters;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.user.entity.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service for operations with matches
 */
@Service
@Slf4j
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamMatchScoreRepository teamMatchScoreRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private Messages messages;
    @Autowired
    private ErrorDescriptionFactory errorDescriptionFactory;

    @Transactional
    public MatchDTO create(CreateMatchRequest request) {
        log.debug("create; request = {}", request);

        checkCreateRequest(request);

        Game game = gameRepository.findOne(request.getGameID());

        Long winnerTeamID = request.getTeamsScore()
                .stream()
                .filter(e -> e.getScore().equals(game.getScoreToWin()))
                .map(TeamScore::getTeamID)
                .findFirst()
                .get();


        List<Long> participantTeamIDs = request.getTeamsScore()
                .stream()
                .map(TeamScore::getTeamID)
                .collect(Collectors.toList());

        List<Team> participantTeams = teamRepository.findAll(participantTeamIDs);

        Map<Long, Team> idTeamMap = participantTeams
                .stream()
                .collect(Collectors.toMap(Team::getId, Function.identity()));

        Match match = new Match();
        match.setTimestamp(request.getTimestamp());
        match.setGame(game);
        match.setWinner(idTeamMap.get(winnerTeamID));

        match = matchRepository.save(match);

        Set<TeamMatchScore> teamMatchScores = new HashSet<>(request.getTeamsScore().size());

        for (TeamScore teamScore : request.getTeamsScore()) {
            Team team = idTeamMap.get(teamScore.getTeamID());

            TeamMatchScore teamMatchScore = new TeamMatchScore();
            teamMatchScore.setPk(TeamMatchScorePK.builder().matchID(match.getId()).teamID(team.getId()).build());
            teamMatchScore.setMatch(match);
            teamMatchScore.setScore(teamScore.getScore());
            teamMatchScore.setTeam(team);

            teamMatchScores.add(teamMatchScoreRepository.save(teamMatchScore));
        }

        match.setTeamsMatchScore(teamMatchScores);

        for (Team team : participantTeams) {
            team.setNumberOfMatches(team.getNumberOfMatches() + 1);
            if (team.getId().equals(winnerTeamID)) {
                team.setNumberOfWinMatches(team.getNumberOfWinMatches() + 1);
            }
        }

        teamRepository.save(participantTeams);


        log.debug("create; match with id = {} created", match.getId());
        return convert(match);
    }

    public MatchDTO getMatch(Long id) {
        log.debug("getMatch; id = {}", id);

        return convert(matchRepository.findOne(id));
    }

    public List<MatchDTO> getMatches(MatchesFilters filters) {
        log.trace("getMatches; enter");

        List<MatchDTO> result = matchRepository
                .findAll(MatchSpecifications.createGetMatchesSpecification(filters),
                         MatchSpecifications.createGetMatchesSort(filters))
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());

        log.trace("getMatches; return {} matches", result.size());
        return result;
    }

    private MatchDTO convert(Match match) {
        return conversionService.convert(match, MatchDTO.class);
    }

    private void checkCreateRequest(CreateMatchRequest request) {
        List<ErrorDescription> errors = new ArrayList<>();

        List<Long> teamIDs = request.getTeamsScore()
                .stream()
                .map(TeamScore::getTeamID)
                .distinct()
                .collect(Collectors.toList());

        List<Team> teams = teamRepository.findAll(teamIDs);
        Game game = gameRepository.findOne(request.getGameID());

        if (game == null) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("gameID",
                                                 messages.getMessage("game.errors.notFound", request.getGameID()));
            errors.add(error);
        }


        if (CollectionUtils.size(request.getTeamsScore()) != CollectionUtils.size(teams)) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("teamsScore",
                                                 messages.getMessage("match.errors.incorrectTeamsCount"));
            errors.add(error);
        } else if (CollectionUtils.size(teamIDs) != game.getTeamCountInMatch()) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("teamsScore",
                                                 messages.getMessage("match.errors.incorrectTeamsCountForGame"));
            errors.add(error);
        }

        List<User> users = teams.stream().flatMap(e -> e.getUsers().stream()).collect(Collectors.toList());
        long uniqueUsers = users.stream().map(User::getId).distinct().count();

        if (CollectionUtils.size(users) != uniqueUsers) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("teamsScore",
                                                 messages.getMessage(
                                                         "match.errors.existsDuplicateUserInDifferentTeam"));
            errors.add(error);
        }

        Long scoreToWin = game.getScoreToWin();

        boolean isAllTeamHaveCorrectScore = request.getTeamsScore().stream().allMatch(
                e -> e.getScore() >= 0 && e.getScore() <= scoreToWin);

        if (!isAllTeamHaveCorrectScore) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("teamsScore",
                                                 messages.getMessage("match.errors.existTeamWithIncorrectScore"));
            errors.add(error);
        }

        boolean isOnlyOneWinner = request.getTeamsScore().stream().filter(
                e -> e.getScore().equals(game.getScoreToWin())).count() == 1;

        if (!isOnlyOneWinner) {
            ErrorDescription error = errorDescriptionFactory
                    .createInvalidParameterError("teamsScore",
                                                 messages.getMessage("match.errors.moreThanOneWinner"));
            errors.add(error);
        }

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
