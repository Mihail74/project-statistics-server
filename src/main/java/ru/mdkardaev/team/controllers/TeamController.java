package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.requests.GetTeamRequest;
import ru.mdkardaev.team.responses.CreateTeamResponse;
import ru.mdkardaev.team.responses.GetTeamResponse;
import ru.mdkardaev.team.services.TeamService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/teams")
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create team", notes = "Create team and send invite for members from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team is successfully created", response = CreateTeamResponse.class),
            @ApiResponse(code = 409, message = "Invalid parameters passed to request")
    })
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateTeamRequest request,
                                        @AuthenticationPrincipal UserDetails principal) {
        Long teamID = teamService.create(request, principal.getUsername());

        TeamDTO team = teamService.getTeam(teamID);
        return ResponseEntity.ok(new CreateTeamResponse(team));
    }

    @RequestMapping(path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return teams list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return teamslist", response = GetTeamResponse.class),
    })
    public ResponseEntity<?> getUsers(GetTeamRequest request) {
        List<TeamDTO> teams = teamService.getTeams(request);
        return ResponseEntity.ok(new GetTeamResponse(teams));
    }
}
