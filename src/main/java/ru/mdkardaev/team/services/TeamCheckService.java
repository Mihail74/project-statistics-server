package ru.mdkardaev.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.EntityNotFoundException;
import ru.mdkardaev.team.repository.TeamRepository;

/**
 * Service for checking team
 */
@Service
public class TeamCheckService {

    @Autowired
    private TeamRepository teamRepository;

    /**
     * check that team with specified id exist
     *
     * @throws ru.mdkardaev.common.exceptions.EntityNotFoundException if team doesn't exist
     */
    public void checkTeamExist(Long id) {
        if (teamRepository.findOne(id) == null) {
            throw new EntityNotFoundException();
        }
    }
}
