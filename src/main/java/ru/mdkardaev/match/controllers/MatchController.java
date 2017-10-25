package ru.mdkardaev.match.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.match.requests.CreateMatchRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/matches")
@Api(tags = SwaggerConfig.Tags.MATCHES)
public class MatchController {

    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create match")
    @ApiResponse(code = 200, message = "Match is successfully created")
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateMatchRequest request) {
//        GameDTO game = gameService.create(request);
        return ResponseEntity.ok().build();
    }
}
