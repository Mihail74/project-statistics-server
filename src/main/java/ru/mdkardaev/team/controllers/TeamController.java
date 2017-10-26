package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.requests.CreateTeamRequest;
import ru.mdkardaev.team.responses.TeamAndInvites;
import ru.mdkardaev.team.services.TeamService;
import ru.mdkardaev.user.services.UserService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "api/teams",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private InviteService inviteService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create team",
            notes = "Create team and send invite for members from request",
            response = TeamAndInvites.class)
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateTeamRequest request,
                                        @AuthenticationPrincipal UserDetails principal) {
        TeamAndInvites teamAndInvitedMembers = teamService.createTeamAndInviteMembers(request, principal.getUsername());
        return ResponseEntity.ok(teamAndInvitedMembers);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get team", response = TeamAndInvites.class)
    public ResponseEntity<?> getTeam(@PathVariable("id") Long id) {
        TeamDTO team = teamService.getTeam(id);

        List<InviteDTO> invitedUsers = null;
        if (team.getFormingStatus() == TeamFormingStatus.FORMING) {
            invitedUsers = inviteService.getUsersInvitedInTeam(id);
        }

        return ResponseEntity.ok(new TeamAndInvites(team, invitedUsers));
    }
}
