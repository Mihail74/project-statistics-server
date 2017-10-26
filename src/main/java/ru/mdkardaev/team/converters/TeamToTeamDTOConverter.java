package ru.mdkardaev.team.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.stream.Collectors;


@Component
public class TeamToTeamDTOConverter implements Converter<Team, TeamDTO> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public TeamDTO convert(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .formingStatus(team.getFormingStatus())

                .game(conversionService.convert(team.getGame(), GameDTO.class))
                .users(team.getUsers()
                               .stream()
                               .map(e -> conversionService.convert(e, UserDTO.class))
                               .collect(Collectors.toList()))
                .leader(conversionService.convert(team.getLeader(), UserDTO.class))
                .build();
    }
}
