package ru.mdkardaev.match.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.match.dtos.MatchDTO;
import ru.mdkardaev.match.dtos.TeamMatchScoreDTO;
import ru.mdkardaev.match.entity.Match;
import ru.mdkardaev.team.dtos.TeamOnlyNameDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MatchToMatchDTOConverter implements Converter<Match, MatchDTO> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public MatchDTO convert(Match from) {
        Set<TeamMatchScoreDTO> teamsMatchScore
                = from.getTeamsMatchScore()
                      .stream()
                      .map(e -> new TeamMatchScoreDTO(conversionService.convert(e.getTeam(), TeamOnlyNameDTO.class),
                                                      e.getScore()))
                      .collect(Collectors.toSet());

        return MatchDTO.builder()
                       .id(from.getId())
                       .timestamp(from.getTimestamp())
                       .game(conversionService.convert(from.getGame(), GameDTO.class))
                       .winnerTeam(conversionService.convert(from.getWinner(), TeamOnlyNameDTO.class))
                       .teamsMatchScore(teamsMatchScore)
                       .build();
    }
}
