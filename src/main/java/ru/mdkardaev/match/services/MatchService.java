package ru.mdkardaev.match.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.match.entity.Match;
import ru.mdkardaev.match.entity.TeamMatchScore;
import ru.mdkardaev.match.entity.TeamMatchScorePK;
import ru.mdkardaev.match.repository.MatchRepository;
import ru.mdkardaev.match.repository.TeamMatchScoreRepository;
import ru.mdkardaev.match.requests.CreateMatchRequest;
import ru.mdkardaev.match.requests.TeamScore;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Transactional
    public void create(CreateMatchRequest request) {
        Team winnerTeam = teamRepository.findOne(request.getWinnerTeamID());
        Game game = gameRepository.findOne(request.getGameID());

        List<Long> participantTeamIDs = request.getTeamsScore()
                                               .stream()
                                               .map(TeamScore::getTeamID)
                                               .collect(Collectors.toList());

        Map<Long, Team> participantTeams = teamRepository.findAll(participantTeamIDs)
                                                         .stream()
                                                         .collect(Collectors.toMap(Team::getId, Function.identity()));

        Match match = new Match();
        match.setTimestamp(request.getTimestamp());
        match.setGame(game);
        match.setWinner(winnerTeam);

        match = matchRepository.save(match);

        for (TeamScore teamScore : request.getTeamsScore()) {
            Team team = participantTeams.get(teamScore.getTeamID());

            TeamMatchScore teamMatchScore = new TeamMatchScore();
            teamMatchScore.setPk(TeamMatchScorePK.builder().matchID(match.getId()).teamID(team.getId()).build());
            teamMatchScore.setMatch(match);
            teamMatchScore.setScore(teamScore.getScore());
            teamMatchScore.setTeam(team);

            teamMatchScoreRepository.save(teamMatchScore);
        }
        //TODO: обновить элементы статистики
    }
}
