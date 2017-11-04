package ru.mdkardaev.game.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.common.exceptions.enums.SQLStates;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.game.requests.CreateGameRequest;
import ru.mdkardaev.game.responses.CreateGameResponse;
import ru.mdkardaev.game.responses.GetGamesResponse;
import ru.mdkardaev.game.services.GameService;

import javax.validation.Valid;

import java.util.List;

/**
 * Rest controller for operations with games
 */
@RestController
@RequestMapping(value = "/api/games",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = SwaggerConfig.Tags.GAMES)
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create game",
            response = CreateGameResponse.class,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateGameRequest request) {
        log.info("createGame; request is {}", request);

        GameDTO game = gameService.create(request);

        log.info("createGame; Game with id = {} created", game.getId());
        return ResponseEntity.ok(new CreateGameResponse(game));
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of games", response = GetGamesResponse.class)
    public ResponseEntity<?> getGames() {
        log.debug("getGames; enter");

        List<GameDTO> games = gameService.getGames();

        log.debug("getGames; returns {} games", games.size());
        return ResponseEntity.ok(new GetGamesResponse(games));
    }
}
