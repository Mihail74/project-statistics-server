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
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.requests.EditTeamRequest;
import ru.mdkardaev.team.responses.GetInvitesInTeamResponse;
import ru.mdkardaev.team.responses.GetTeamResponse;
import ru.mdkardaev.team.services.UpdateTeamService;

@RestController
@RequestMapping(value = "/api/me/teams",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class MeTeamController {

    @Autowired
    private UpdateTeamService updateTeamService;

    @ApiOperation(value = "Form team", notes = "Only team leader can form team", response = GetInvitesInTeamResponse.class)
    @RequestMapping(path = "/{id}/form", method = RequestMethod.POST)
    public ResponseEntity<?> form(@PathVariable("id") Long id,
                                  @AuthenticationPrincipal UserDetails principal) {
        TeamDTO team = updateTeamService.formTeam(id, Long.valueOf(principal.getUsername()));
        return ResponseEntity.ok(new GetTeamResponse(team));
    }

    @ApiOperation(value = "Edit team", notes = "Only team leader can edit team", response = GetTeamResponse.class)
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity<?> edit(@PathVariable("id") Long id,
                                  @RequestBody EditTeamRequest request,
                                  @AuthenticationPrincipal UserDetails principal) {
        TeamDTO team = updateTeamService.editTeam(request, id, Long.valueOf(principal.getUsername()));
        return ResponseEntity.ok(new GetTeamResponse(team));
    }
}
