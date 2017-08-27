package ru.mdkardaev.player.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.player.dtos.PlayerDTO;
import ru.mdkardaev.player.requests.GetPlayersRequest;
import ru.mdkardaev.player.requests.RegisterPlayerRequest;
import ru.mdkardaev.player.responses.GetPlayersResponse;
import ru.mdkardaev.player.services.PlayerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/player")
@Api(tags = {"players"}, description = "Operations with players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is successfully  registered"),
            @ApiResponse(code = 409, message = "User with the specified email already exist")
    })
    public ResponseEntity<?> register(@RequestBody @Valid RegisterPlayerRequest request) {
        playerService.register(request);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/players",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return players list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return players list", response = GetPlayersResponse.class),
    })
    public ResponseEntity<?> getPlayers(GetPlayersRequest request) {
        List<PlayerDTO> players = playerService.getPlayers();
        return ResponseEntity.ok(new GetPlayersResponse(players));
    }
}
