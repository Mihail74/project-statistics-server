package ru.mdkardaev.invite.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.invite.requests.AcceptInviteRequest;
import ru.mdkardaev.invite.requests.DeclineInviteRequest;
import ru.mdkardaev.invite.responses.GetMyInviteResponse;
import ru.mdkardaev.invite.responses.GetMyInvitesResponse;
import ru.mdkardaev.invite.services.InviteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/me/teams/invites")
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class MeInviteController {

    @Autowired
    private InviteService inviteService;

    @RequestMapping(path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get my invites to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Invites"),
    })
    public ResponseEntity<?> getMyInvites(@AuthenticationPrincipal UserDetails principal) {
        List<InviteDTO> invites = inviteService.getUserInvites(principal.getUsername(), InviteStatus.NEW);
        return ResponseEntity.ok(new GetMyInvitesResponse(invites));
    }

    @RequestMapping(path = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get my invite to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Invite"),
    })
    public ResponseEntity<?> getMyInvite(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails principal) {
        InviteDTO invite = inviteService.getInvite(id);
        return ResponseEntity.ok(new GetMyInviteResponse(invite));
    }

    @RequestMapping(path = "/accept",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Accept invite to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Accept invite to team"),
    })
    public ResponseEntity<?> acceptInvite(@RequestBody @Valid AcceptInviteRequest request,
                                          @AuthenticationPrincipal UserDetails principal) {
        inviteService.acceptInvitation(request.getId(), principal.getUsername());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/decline",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Decline invite to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Decline invite to team"),
    })
    public ResponseEntity<?> declineInvite(@RequestBody @Valid DeclineInviteRequest request,
                                           @AuthenticationPrincipal UserDetails principal) {
        inviteService.acceptInvitation(request.getInviteID(), principal.getUsername());
        return ResponseEntity.ok().build();
    }
}
