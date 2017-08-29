package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.game.requests.CreateGameRequest;
import ru.mdkardaev.game.services.GameService;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.services.TeamService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/teams")
@Api(tags = {"teams"}, description = "Operations with teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create team without members")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game is successfully  registered"),
            @ApiResponse(code = 409, message = "Game with the specified name already exist")
    })
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateTeamRequest request) {
        teamService.create(request);
        return ResponseEntity.ok().build();
    }
}