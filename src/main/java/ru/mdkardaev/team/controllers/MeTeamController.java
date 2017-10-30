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
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.responses.GetMyTeamsResponse;
import ru.mdkardaev.team.responses.TeamAndInvites;
import ru.mdkardaev.team.services.GetTeamsService;
import ru.mdkardaev.team.services.UpdateTeamService;
import ru.mdkardaev.team.specifications.TeamsFilters;

import java.util.List;

@RestController
@RequestMapping(value = "api/me/teams",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class MeTeamController {

    @Autowired
    private GetTeamsService getTeamsService;
    @Autowired
    private UpdateTeamService updateTeamService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get my teams", response = GetMyTeamsResponse.class)
    public ResponseEntity<?> getUserTeams(@RequestParam("formingStatus") TeamFormingStatus formingStatus,
                                          @AuthenticationPrincipal UserDetails principal) {
        TeamsFilters filters = TeamsFilters.builder()
                                           .memberUserLogin(principal.getUsername())
                                           .formingStatus(formingStatus)
                                           .build();
        List<TeamDTO> userTeams = getTeamsService.getTeams(filters);
        return ResponseEntity.ok(new GetMyTeamsResponse(userTeams));
    }

    @ApiOperation(value = "Form team", response = Void.class)
    @RequestMapping(path = "/{id}/form", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> formTeam(@PathVariable("id") Long id,
                                      @AuthenticationPrincipal UserDetails principal) {
        //TODO: check principal is leader
        TeamDTO team = updateTeamService.formTeam(id);
        return ResponseEntity.ok(new TeamAndInvites(team));
    }
}
