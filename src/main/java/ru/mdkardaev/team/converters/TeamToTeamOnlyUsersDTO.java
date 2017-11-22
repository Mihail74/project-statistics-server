package ru.mdkardaev.team.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.team.dtos.TeamOnlyUsersDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.stream.Collectors;

@Component
public class TeamToTeamOnlyUsersDTO implements Converter<Team, TeamOnlyUsersDTO> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public TeamOnlyUsersDTO convert(Team from) {
        return TeamOnlyUsersDTO.builder()
                               .id(from.getId())
                               .users(from.getUsers()
                                          .stream()
                                          .map(e -> conversionService.convert(e, UserDTO.class))
                                          .collect(Collectors.toList()))
                               .name(from.getName())
                               .build();
    }
}
