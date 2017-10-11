package ru.mdkardaev.game.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.exceptions.GameAlreadyExist;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.game.requests.CreateGameRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameService {

    @Autowired
    private DBExceptionUtils dbExceptionUtils;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ConversionService conversionService;

    /**
     * return created game
     */
    public GameDTO create(CreateGameRequest request) {
        Game game = Game.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        try {
            game = gameRepository.save(game);
        } catch (DataIntegrityViolationException e) {
            dbExceptionUtils
                    .conditionThrowNewException(e,
                                                SQLStates.UNIQUE_VIOLATION,
                                                () -> new GameAlreadyExist(String.format(
                                                        "Game with name: [%s] already exist.",
                                                        request.getName())));
            log.error(e.getMessage(), e);
            throw e;
        }
        return conversionService.convert(game, GameDTO.class);
    }

    public List<GameDTO> getGames() {
        return gameRepository.findAll()
                .stream()
                .map(e -> conversionService.convert(e, GameDTO.class))
                .collect(Collectors.toList());
    }
}
