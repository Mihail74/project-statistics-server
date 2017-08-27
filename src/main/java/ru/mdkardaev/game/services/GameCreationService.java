package ru.mdkardaev.game.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.exceptions.GameAlreadyExist;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.game.requests.CreateGameRequest;

@Service
@Slf4j
public class GameCreationService {

    @Autowired
    private DBExceptionUtils dbExceptionUtils;
    @Autowired
    private GameRepository gameRepository;

    public void create(CreateGameRequest request) {
        Game game = Game.builder()
                        .name(request.getName())
                        .scoreToWin(request.getScoreToWin())
                        .build();
        try {
            gameRepository.save(game);
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
    }
}
