package ru.mdkardaev.player.controller;

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
import ru.mdkardaev.player.handler.PlayerRegistrationService;
import ru.mdkardaev.player.requests.RegisterRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/player")
@Api(tags = {"players"}, description = "Operations with players")
public class PlayerController {

    @Autowired
    private PlayerRegistrationService playerRegistrationService;

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is successfully  registered"),
            @ApiResponse(code = 409, message = "User with the specified email already exist")
    })
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        playerRegistrationService.register(request);
        return ResponseEntity.ok().build();
    }
}
