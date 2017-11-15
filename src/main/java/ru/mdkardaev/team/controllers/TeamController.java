package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.requests.GetTeamsRequest;
import ru.mdkardaev.team.responses.GetInvitesInTeamResponse;
import ru.mdkardaev.team.responses.GetTeamResponse;
import ru.mdkardaev.team.responses.GetTeamsResponse;
import ru.mdkardaev.team.services.GetTeamService;
import ru.mdkardaev.team.services.GetTeamsService;
import ru.mdkardaev.team.services.TeamCreationService;
import ru.mdkardaev.team.specifications.TeamsFilters;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/teams",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.TEAMS})
@Slf4j
public class TeamController {

    @Autowired
    private TeamCreationService teamCreationService;
    @Autowired
    private GetTeamService getTeamService;
    @Autowired
    private GetTeamsService getTeamsService;
    @Autowired
    private InviteService inviteService;

    @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create team",
            notes = "Create team and send invite for members from request",
            response = GetTeamResponse.class)
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamRequest request,
                                        @AuthenticationPrincipal UserDetails principal) {
        log.debug("createTeam; request is {}", request);

        TeamDTO team = teamCreationService.createTeamAndInviteMembers(request, Long.valueOf(principal.getUsername()));

        log.debug("createTeam; team with id = {} created", team.getId());

        return ResponseEntity.ok(new GetTeamResponse(team));
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of teams", notes = "List of teams that match the specified filters",
            response = GetTeamsResponse.class)
    public ResponseEntity<?> getTeams(@Valid GetTeamsRequest request) {
        log.debug("getTeams; request is {}", request);

        TeamsFilters filters = TeamsFilters.builder()
                                           .gameID(request.getGameID())
                                           .formingStatus(request.getFormingStatus())
                                           .memberID(request.getMemberID())
                                           .build();
        List<TeamDTO> teams = getTeamsService.getTeams(filters);

        log.debug("getTeams; returns {} teams", teams.size());
        return ResponseEntity.ok(new GetTeamsResponse(teams));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get team", response = GetTeamResponse.class)
    public ResponseEntity<?> getTeam(@PathVariable("id") Long id) {
        log.debug("getTeam; Get team with id = {}", id);

        TeamDTO team = getTeamService.getTeam(id);

        log.debug("getTeam; Team wih id = {} is returning", team.getId());
        return ResponseEntity.ok(new GetTeamResponse(team));
    }

    @RequestMapping(path = "/{id}/invites", method = RequestMethod.GET)
    @ApiOperation(value = "Get invites in team", notes = "", response = GetInvitesInTeamResponse.class)
    public ResponseEntity<?> getInvitesInTeam(@PathVariable("id") Long id) {
        log.debug("getInvitesInTeam; Get invites in team with id = {}", id);

        List<InviteDTO> invites = inviteService.getInvitesInTeam(id);

        log.debug("getInvitesInTeam; Invites in team wih id = {} is returning", id);
        return ResponseEntity.ok(new GetInvitesInTeamResponse(invites));
    }
}
