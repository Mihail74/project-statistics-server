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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.invite.responses.GetMyInviteResponse;
import ru.mdkardaev.invite.responses.GetMyInvitesResponse;
import ru.mdkardaev.invite.services.InviteService;

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
    @ApiResponse(code = 200, message = "My new invites")
    public ResponseEntity<?> getMyInvites(@AuthenticationPrincipal UserDetails principal) {
        List<InviteDTO> invites = inviteService.getUserInvites(principal.getUsername(), InviteStatus.NEW);
        return ResponseEntity.ok(new GetMyInvitesResponse(invites));
    }

    @RequestMapping(path = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get my invite to team")
    @ApiResponse(code = 200, message = "Invite", response = GetMyInviteResponse.class)
    public ResponseEntity<?> getMyInvite(@PathVariable("id") Long id,
                                         @AuthenticationPrincipal UserDetails principal) {
        InviteDTO invite = inviteService.getInvite(id);
        return ResponseEntity.ok(new GetMyInviteResponse(invite));
    }

    @RequestMapping(path = "/{id}/accept",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Accept invite to team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Accept invite in team"),
    })
    public ResponseEntity<?> acceptInvite(@PathVariable("id") Long id,
                                          @AuthenticationPrincipal UserDetails principal) {
        inviteService.acceptInvitation(id, principal.getUsername());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "{id}/decline",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Decline invite to team")
    @ApiResponse(code = 200, message = "Decline invite in team")
    public ResponseEntity<?> declineInvite(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal UserDetails principal) {
        inviteService.declineInvitation(id, principal.getUsername());
        return ResponseEntity.ok().build();
    }
}
