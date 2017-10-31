package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.requests.GetTeamsRequest;
import ru.mdkardaev.team.responses.GetTeamsResponse;
import ru.mdkardaev.team.responses.TeamAndInvites;
import ru.mdkardaev.team.services.GetTeamService;
import ru.mdkardaev.team.services.GetTeamsService;
import ru.mdkardaev.team.services.TeamCreationService;
import ru.mdkardaev.team.specifications.TeamsFilters;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/teams",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.TEAMS})
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
            response = TeamAndInvites.class)
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamRequest request,
                                        @AuthenticationPrincipal UserDetails principal) {
        TeamAndInvites teamAndInvitedMembers = teamCreationService
                .createTeamAndInviteMembers(request, principal.getUsername());
        return ResponseEntity.ok(teamAndInvitedMembers);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of teams", notes = "List of teams that match the specified filters",
            response = GetTeamsResponse.class)
    public ResponseEntity<?> getTeams(GetTeamsRequest request) {
        TeamsFilters filters = TeamsFilters.builder()
                                           .gameID(request.getGameID())
                                           .formingStatus(request.getFormingStatus())
                                           .memberID(request.getMemberID())
                                           .build();
        List<TeamDTO> teams = getTeamsService.getTeams(filters);
        return ResponseEntity.ok(new GetTeamsResponse(teams));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get team", response = TeamAndInvites.class)
    public ResponseEntity<?> getTeam(@PathVariable("id") Long id) {
        TeamDTO team = getTeamService.getTeam(id);

        List<InviteDTO> invitedUsers = null;
        if (team.getFormingStatus() == TeamFormingStatus.FORMING) {
            invitedUsers = inviteService.getInvitesInTeam(id);
        }

        return ResponseEntity.ok(new TeamAndInvites(team, invitedUsers));
    }
}
