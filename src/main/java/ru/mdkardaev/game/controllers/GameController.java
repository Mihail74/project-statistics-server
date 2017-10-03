package ru.mdkardaev.game.controllers;

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
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.game.requests.CreateGameRequest;
import ru.mdkardaev.game.responses.CreateGameResponse;
import ru.mdkardaev.game.responses.GetGamesResponse;
import ru.mdkardaev.game.services.GameService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/api/games")
@Api(tags = SwaggerConfig.Tags.GAMES)
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game is successfully created", response = CreateGameResponse.class),
            @ApiResponse(code = 400, message = "Game with the specified name already exist or name is empty")
    })
    public ResponseEntity<?> createGame(@RequestBody @Valid CreateGameRequest request) {
        Long gameId = gameService.create(request);
        GameDTO game = gameService.getGame(gameId);
        return ResponseEntity.ok(new CreateGameResponse(game));
    }

    @RequestMapping(path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get games list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return games list", response = GetGamesResponse.class)
    })
    public ResponseEntity<?> getGames() {
        List<GameDTO> games = gameService.getGames();
        return ResponseEntity.ok(new GetGamesResponse(games));
    }
}
