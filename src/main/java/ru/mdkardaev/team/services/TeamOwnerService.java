package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;

/**
 * Service that answer who owner of specified team
 */
@Service
public class TeamOwnerService {

    @Autowired
    private TeamRepository teamRepository;

    /**
     * Check that that team with specified id exist.
     *
     * @return Is user with specified login is leader of team with specified id?
     */
    public boolean isLeaderTeam(Long userID, Long teamID) {
        Team team = teamRepository.findOne(teamID);
        return team.getLeader().getId().equals(userID);
    }
}
