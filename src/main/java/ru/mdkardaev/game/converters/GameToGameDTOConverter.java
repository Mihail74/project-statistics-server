package ru.mdkardaev.game.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.game.entity.Game;

@Component
public class GameToGameDTOConverter implements Converter<Game, GameDTO> {
    @Override
    public GameDTO convert(Game from) {
        return GameDTO.builder()
                      .id(from.getId())
                      .name(from.getName())
                      .scoreToWin(from.getScoreToWin())
                      .build();
    }
}
