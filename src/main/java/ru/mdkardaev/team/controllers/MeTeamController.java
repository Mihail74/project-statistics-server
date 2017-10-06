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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.responses.GetMeTeamsResponse;
import ru.mdkardaev.team.services.TeamService;

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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User's teams", response = GetMeTeamsResponse.class)
    })
    public ResponseEntity<?> getUserTeams(@RequestParam("formingStatus") TeamFormingStatus formingStatus, @AuthenticationPrincipal UserDetails principal) {
        List<TeamDTO> userTeams = teamService.getUserTeams(principal.getUsername(), formingStatus);
        return ResponseEntity.ok(new GetMeTeamsResponse(userTeams));
    }
}
