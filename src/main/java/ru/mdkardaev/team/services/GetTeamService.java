package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;

/**
 * Service that handles requests to retrieve information about the team
 */
@Service
public class GetTeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ConversionService conversionService;

    /**
     * Return team with specified ID
     */
    public TeamDTO getTeam(Long id) {
        Team team = teamRepository.findOne(id);
        return conversionService.convert(team, TeamDTO.class);
    }
}
