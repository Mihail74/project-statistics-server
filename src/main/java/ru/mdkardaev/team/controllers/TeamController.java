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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.responses.TeamResponse;
import ru.mdkardaev.team.services.InviteService;
import ru.mdkardaev.team.services.TeamService;
import ru.mdkardaev.user.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/teams")
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private InviteService inviteService;

    @RequestMapping(path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team", response = TeamResponse.class),
    })
    public ResponseEntity<?> getTeam(@RequestParam("id") Long id) {
        TeamDTO team = teamService.getTeam(id);
        return ResponseEntity.ok(new TeamResponse(team));
    }


    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create team", notes = "Create team and send invite for members from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team is successfully created", response = TeamResponse.class),
            @ApiResponse(code = 409, message = "Invalid parameters passed to request")
    })
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateTeamRequest request,
                                        @AuthenticationPrincipal UserDetails principal) {
        Long teamID = teamService.create(request, principal.getUsername());

        TeamDTO team = teamService.getTeam(teamID);
        return ResponseEntity.ok(new TeamResponse(team));
    }
}
