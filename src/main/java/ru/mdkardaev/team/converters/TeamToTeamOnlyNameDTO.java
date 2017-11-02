package ru.mdkardaev.team.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.team.dtos.TeamOnlyNameDTO;
import ru.mdkardaev.team.entity.Team;

@Component
public class TeamToTeamOnlyNameDTO implements Converter<Team, TeamOnlyNameDTO> {

    @Override
    public TeamOnlyNameDTO convert(Team from) {
        return TeamOnlyNameDTO.builder()
                              .id(from.getId())
                              .name(from.getName())
                              .build();
    }
}
