package ru.mdkardaev.team.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.dtos.TeamsPage;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.specifications.SortField;
import ru.mdkardaev.team.specifications.TeamSpecifications;
import ru.mdkardaev.team.specifications.TeamsFilters;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that handles requests to retrieve information about the teams
 */
@Service
@Slf4j
public class GetTeamsService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ConversionService conversionService;

    /**
     * Returns teams that match specified filters
     */
    @Transactional
    public TeamsPage getTeams(TeamsFilters filters) {
        Specification<Team> specification = TeamSpecifications.createGetTeamsSpecification(filters);
        Sort sort = new Sort(new Sort.Order(SortField.NAME.getProperty()));

        Page<Team> page;
        if (filters.getPageNumber() == null) {
            List<Team> teams = teamRepository.findAll(specification, sort);
            page = new PageImpl<>(teams, null, teams.size());
        } else {
            PageRequest pageRequest = new PageRequest(filters.getPageNumber(), DEFAULT_PAGE_SIZE, sort);
            page = teamRepository.findAll(specification, pageRequest);
        }

        List<TeamDTO> result = page.getContent()
                .stream()
                .map(e -> conversionService.convert(e, TeamDTO.class))
                .collect(Collectors.toList());

        log.trace("getMatches; return {} matches", result.size());
        return new TeamsPage(result, page.getTotalPages(), page.getTotalElements(),
                page.getNumber() + 1, page.getNumberOfElements());
    }
}
