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
import ru.mdkardaev.team.dtos.TeamInviteDTO;
import ru.mdkardaev.team.requests.AcceptTeamInviteRequest;
import ru.mdkardaev.team.requests.DeclineTeamInviteRequest;
import ru.mdkardaev.team.responses.GetMyTeamInvitesResponse;
import ru.mdkardaev.team.services.TeamInviteService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/me/teams/invites")
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class MeTeamInviteController {

    @Autowired
    private TeamInviteService teamInviteService;

    @RequestMapping(path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get my invites to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Invites"),
    })
    public ResponseEntity<?> getMyInvites(@AuthenticationPrincipal UserDetails principal) {
        List<TeamInviteDTO> invites = teamInviteService.getInvites(principal.getUsername(), null);
        return ResponseEntity.ok(new GetMyTeamInvitesResponse(invites));
    }

    @RequestMapping(path = "/accept",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Accept invite to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Accept invite to team"),
    })
    public ResponseEntity<?> acceptInvite(@RequestBody @Valid AcceptTeamInviteRequest request,
                                          @AuthenticationPrincipal UserDetails principal) {
        teamInviteService.acceptInvitation(request.getInviteID(), principal.getUsername());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/decline",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Decline invite to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Decline invite to team"),
    })
    public ResponseEntity<?> declineInvite(@RequestBody @Valid DeclineTeamInviteRequest request,
                                           @AuthenticationPrincipal UserDetails principal) {
        teamInviteService.acceptInvitation(request.getInviteID(), principal.getUsername());
        return ResponseEntity.ok().build();
    }
}
