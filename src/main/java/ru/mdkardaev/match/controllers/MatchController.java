package ru.mdkardaev.match.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.match.dtos.MatchDTO;
import ru.mdkardaev.match.requests.CreateMatchRequest;
import ru.mdkardaev.match.requests.GetMatchesRequest;
import ru.mdkardaev.match.responses.GetMatchResponse;
import ru.mdkardaev.match.responses.GetMatchesResponse;
import ru.mdkardaev.match.services.MatchService;

import javax.validation.Valid;

import java.util.List;

/**
 * Rest controller for operations with matches
 */
@RestController
@RequestMapping(value = "/api/matches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = SwaggerConfig.Tags.MATCHES)
@Slf4j
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create match", response = GetMatchResponse.class)
    public ResponseEntity<?> createMatch(@RequestBody @Valid CreateMatchRequest request) {
        log.debug("createMatch; request is {}", request);

        MatchDTO match = matchService.create(request);

        log.debug("createMatch; Match with id = {} created", match.getId());
        return ResponseEntity.ok(new GetMatchResponse(match));
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get matches", notes = "List of matches that match the specified filters",
            response = GetMatchesResponse.class)
    public ResponseEntity<?> get(GetMatchesRequest request) {
        log.debug("get; request is {}", request);

        List<MatchDTO> matches = matchService.getMatches();

        log.debug("get; returns {} matches", matches.size());
        return ResponseEntity.ok(new GetMatchesResponse(matches));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get match", response = GetMatchResponse.class)
    public ResponseEntity<?> getMatch(@PathVariable("id") Long id) {
        log.debug("getMatch; Get match with id = {}", id);

        MatchDTO match = matchService.getMatch(id);

        log.debug("getMatch; Match wih id = {} is returning", match.getId());
        return ResponseEntity.ok(new GetMatchResponse(match));
    }
}
