package ru.mdkardaev.game.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.game.repository.GameRepository;
import ru.mdkardaev.game.requests.CreateGameRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameService {

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
                        .scoreToWin(request.getScoreToWin())
                        .teamCountInMatch(request.getTeamCountInMatch())
                        .memberCountInTeam(request.getMemberCountInTeam())
                        .build();

        game = gameRepository.save(game);
        return conversionService.convert(game, GameDTO.class);
    }

    /**
     * return all games
     */
    public List<GameDTO> getGames() {
        return gameRepository.findAll()
                             .stream()
                             .map(e -> conversionService.convert(e, GameDTO.class))
                             .collect(Collectors.toList());
    }
}
