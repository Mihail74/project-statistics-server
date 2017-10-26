package ru.mdkardaev.game.controllers;

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
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.game.requests.CreateGameRequest;
import ru.mdkardaev.game.responses.CreateGameResponse;
import ru.mdkardaev.game.responses.GetGamesResponse;
import ru.mdkardaev.game.services.GameService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/api/games",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = SwaggerConfig.Tags.GAMES)
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of games", response = GetGamesResponse.class)
    public ResponseEntity<?> getGames() {
        List<GameDTO> games = gameService.getGames();
        return ResponseEntity.ok(new GetGamesResponse(games));
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create game", response = CreateGameResponse.class)
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateGameRequest request) {
        GameDTO game = gameService.create(request);
        return ResponseEntity.ok(new CreateGameResponse(game));
    }
}
