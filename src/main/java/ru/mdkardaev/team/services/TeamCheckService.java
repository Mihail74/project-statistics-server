package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdkardaev.exceptions.EntityNotFoundException;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;

/**
 * Service for checking team
 */
@Service
public class TeamCheckService {

    @Autowired
    private TeamRepository teamRepository;

    /**
     * check that team with specified id exist and return its
     *
     * @throws EntityNotFoundException if team doesn't exist
     * @return team with specified id
     */
    public Team checkAndGetTeam(Long id) {
        Team team = teamRepository.findOne(id);
        if (team == null) {
            throw new EntityNotFoundException("Team not found");
        }
        return team;
    }
}
