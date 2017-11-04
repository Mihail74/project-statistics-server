package ru.mdkardaev.match.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Transactional
    public MatchDTO create(CreateMatchRequest request) {
        log.debug("create; request = {}", request);

        Team winnerTeam = teamRepository.findOne(request.getWinnerTeamID());
        Game game = gameRepository.findOne(request.getGameID());

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
        match.setWinner(winnerTeam);

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
            team.setNumberOfMatches(team.getNumberOfWinMatches() + 1);
            if (team.getId().equals(winnerTeam.getId())) {
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
}
