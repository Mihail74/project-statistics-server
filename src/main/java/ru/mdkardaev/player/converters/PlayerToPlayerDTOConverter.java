package ru.mdkardaev.player.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.player.dtos.PlayerDTO;
import ru.mdkardaev.player.entity.Player;

@Component
public class PlayerToPlayerDTOConverter implements Converter<Player, PlayerDTO> {

    @Override
    public PlayerDTO convert(Player from) {
        return PlayerDTO.builder()
                        .id(from.getId())
                        .name(from.getName())
                        .email(from.getEmail()).build();
    }
}
