package ru.mdkardaev.match.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.match.repository.MatchRepository;
import ru.mdkardaev.match.repository.TeamMatchScoreRepository;
import ru.mdkardaev.match.requests.CreateMatchRequest;
import ru.mdkardaev.team.repository.TeamRepository;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/matches")
@Api(tags = SwaggerConfig.Tags.MATCHES)
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamMatchScoreRepository teamMatchScoreRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create match")
    @ApiResponse(code = 200, message = "Match is successfully created")
    @Transactional
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateMatchRequest request) {

//        Long gameID = request.getGameID();
//        Long timestamp = request.getTimestamp();
//        Long winnerTeamID = request.getWinnerTeamID();
//
//        Match match = new Match();
//        match.setTimestamp(timestamp);
//        match.setGame(gameRepository.findOne(gameID));
//        match.setWinner(teamRepository.findOne(winnerTeamID));
//
//        match = matchRepository.save(match);
//
//
//        for (TeamScore teamScore : request.getTeamsScore()) {
//            Long teamID = teamScore.getTeamID();
//            Long score = teamScore.getScore();
//
//            TeamMatchScore tms = new TeamMatchScore();
//            tms.setMatch(match);
//            tms.setScore(score);
//            tms.setTeam(teamRepository.findOne(teamID));
//
//            TeamMatchScorePK pk = new TeamMatchScorePK();
//            pk.setTeamID(teamID);
//            pk.setMatchID(match.getId());
//            tms.setPk(pk);
//            teamMatchScoreRepository.save(tms);
//        }

        return ResponseEntity.ok().build();
    }
}
