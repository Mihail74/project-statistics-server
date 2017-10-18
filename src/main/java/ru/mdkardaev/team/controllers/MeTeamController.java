package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
import ru.mdkardaev.team.services.TeamService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/me/teams")
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class MeTeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get my teams")
    @ApiResponse(code = 200, message = "User's teams in specified status", response = GetMyTeamsResponse.class)
    public ResponseEntity<?> getUserTeams(@RequestParam("formingStatus") TeamFormingStatus formingStatus,
                                          @AuthenticationPrincipal UserDetails principal) {
        List<TeamDTO> userTeams = teamService.getUserTeams(principal.getUsername(), formingStatus);
        return ResponseEntity.ok(new GetMyTeamsResponse(userTeams));
    }

    @RequestMapping(path = "/{id}/form",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> formTeam(@PathVariable("id") Long id,
                                      @AuthenticationPrincipal UserDetails principal) {
        //TODO: check principal is leader
        TeamDTO team = teamService.formTeam(id);
        return ResponseEntity.ok(new TeamAndInvites(team));
    }
}
