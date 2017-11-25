package ru.mdkardaev.invite.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import ru.mdkardaev.invite.requests.GetInvitesRequest;
import ru.mdkardaev.invite.responses.GetMyInviteResponse;
import ru.mdkardaev.invite.responses.GetMyInvitesResponse;
import ru.mdkardaev.invite.services.InviteService;
import ru.mdkardaev.invite.services.MeInviteService;
import ru.mdkardaev.invite.specifiations.InvitesFilter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/me/invites",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.INVITES})
@Slf4j
public class MeInviteController {

    @Autowired
    private MeInviteService meInviteService;
    @Autowired
    private InviteService inviteService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get my invites", response = GetMyInvitesResponse.class)
    public ResponseEntity<?> getMyInvites(@Valid GetInvitesRequest request, @AuthenticationPrincipal UserDetails principal) {
        log.debug("getMyInvites; request is {}", request);

        InvitesFilter filters = InvitesFilter.builder()
                                             .userID(Long.valueOf(principal.getUsername()))
                                             .inviteStatus(request.getStatus())
                                             .build();
        List<InviteDTO> invites = inviteService.getUserInvites(filters);

        log.debug("getMyInvites; returns {} teams", invites.size());
        return ResponseEntity.ok(new GetMyInvitesResponse(invites));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get my specified invite", response = GetMyInviteResponse.class)
    public ResponseEntity<?> getMyInvite(@PathVariable("id") Long id,
                                         @AuthenticationPrincipal UserDetails principal) {

        InviteDTO invite = meInviteService.checkAccessAndGetInvite(id, Long.valueOf(principal.getUsername()));

        return ResponseEntity.ok(new GetMyInviteResponse(invite));
    }

    @RequestMapping(path = "/{id}/accept",
            method = RequestMethod.POST)
    @ApiOperation(value = "Accept invite", response = GetMyInviteResponse.class)
    public ResponseEntity<?> acceptInvite(@PathVariable("id") Long id,
                                          @AuthenticationPrincipal UserDetails principal) {
        InviteDTO invite = meInviteService.checkAccessAndAcceptInvite(id, Long.valueOf(principal.getUsername()));
        return ResponseEntity.ok(new GetMyInviteResponse(invite));
    }

    @RequestMapping(path = "{id}/decline",
            method = RequestMethod.POST)
    @ApiOperation(value = "Decline invite", response = GetMyInviteResponse.class)
    public ResponseEntity<?> declineInvite(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal UserDetails principal) {
        InviteDTO invite = meInviteService.checkAccessAndDeclineInvite(id, Long.valueOf(principal.getUsername()));
        return ResponseEntity.ok(new GetMyInviteResponse(invite));
    }

}
