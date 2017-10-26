package ru.mdkardaev.match.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.match.requests.CreateMatchRequest;
import ru.mdkardaev.match.services.MatchService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/matches", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = SwaggerConfig.Tags.MATCHES)
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create match", response = Void.class)
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateMatchRequest request) {
        matchService.create(request);
        return ResponseEntity.ok().build();
    }
}
