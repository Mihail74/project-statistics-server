package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import ru.mdkardaev.common.exceptions.NoAccessException;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.responses.TeamAndInvites;
import ru.mdkardaev.team.services.TeamCheckService;
import ru.mdkardaev.team.services.TeamOwnerService;
import ru.mdkardaev.team.services.UpdateTeamService;

@RestController
@RequestMapping(value = "api/me/teams",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {SwaggerConfig.Tags.TEAMS})
public class MeTeamController {

    @Autowired
    private UpdateTeamService updateTeamService;
    @Autowired
    private TeamOwnerService teamOwnerService;
    @Autowired
    private TeamCheckService teamCheckService;

    @ApiOperation(value = "Form team", response = TeamAndInvites.class)
    @RequestMapping(path = "/{id}/form", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> formTeam(@PathVariable("id") Long id,
                                      @AuthenticationPrincipal UserDetails principal) {

        teamCheckService.checkTeamExist(id);

        if (!teamOwnerService.isLeaderTeam(principal.getUsername(), id)) {
            throw new NoAccessException();
        }

        TeamDTO team = updateTeamService.formTeam(id);

        return ResponseEntity.ok(new TeamAndInvites(team));
    }
}
