package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.specifications.TeamSpecifications;
import ru.mdkardaev.team.specifications.TeamsFilters;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that handles requests to retrieve information about the teams
 */
@Service
public class GetTeamsService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ConversionService conversionService;

    /**
     * Returns teams that match specified filters
     */
    @Transactional
    public List<TeamDTO> getTeams(TeamsFilters filters) {
        Specification<Team> specification = TeamSpecifications.createGetTeamsSpecification(filters);
        return teamRepository.findAll(specification)
                             .stream()
                             .map(e -> conversionService.convert(e, TeamDTO.class))
                             .collect(Collectors.toList());
    }
}
