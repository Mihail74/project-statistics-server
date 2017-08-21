package ru.mdkardaev.player.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.player.repository.PlayerRepository;
import ru.mdkardaev.player.requests.RegisterRequest;
import ru.mdkardaev.player.responses.RegisterResponse;

@RestController
@RequestMapping("api/player")
@Api(tags = {"player"})
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is successfully  registered", response = RegisterResponse.class)
    })
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new RegisterResponse());
    }
}
