package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private TeamCheckService teamCheckService;

    /**
     * Return team with specified ID.
     */
    @Transactional
    public TeamDTO getTeam(Long id) {
        Team team = teamCheckService.checkAndGetTeam(id);
        return conversionService.convert(team, TeamDTO.class);
    }
}
