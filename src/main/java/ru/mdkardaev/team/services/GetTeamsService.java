package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.requests.GetTeamsRequest;
import ru.mdkardaev.team.specifications.TeamSpecifications;

import java.util.ArrayList;
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
    public List<TeamDTO> getTeams(GetTeamsRequest request) {
        List<Specification<Team>> specifications = new ArrayList<>();

        if (request.getGameID() != null) {
            specifications.add(TeamSpecifications.gameID(request.getGameID()));
        }
        if (request.getFormingStatus() != null) {
            specifications.add(TeamSpecifications.formingStatus(request.getFormingStatus()));
        }

        List<Team> teams;

        if (specifications.isEmpty()) {
            teams = teamRepository.findAll();
        } else {
            Specifications<Team> resultSpecification = Specifications.where(specifications.get(0));
            for (int i = 1; i < specifications.size(); ++i) {
                resultSpecification = resultSpecification.and(specifications.get(i));
            }
            teams = teamRepository.findAll(resultSpecification);
        }

        return teams
                .stream()
                .map(e -> conversionService.convert(e, TeamDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * returns teams with specified formingStatus when user with userLogin is member
     */
    public List<TeamDTO> getUserTeams(String userLogin, TeamFormingStatus formingStatus) {
        List<Team> teams = teamRepository.findByUsers_loginAndFormingStatus(userLogin, formingStatus);

        return teams.stream()
                    .map(e -> conversionService.convert(e, TeamDTO.class))
                    .collect(Collectors.toList());
    }
}
