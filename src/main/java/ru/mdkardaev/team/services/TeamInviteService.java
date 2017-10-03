package ru.mdkardaev.team.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.entity.TeamInvite;
import ru.mdkardaev.team.repository.TeamInviteRepository;
import ru.mdkardaev.user.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class TeamInviteService {

    @Autowired
    private TeamInviteRepository teamInviteRepository;

    @Transactional
    public void inviteUsersToTeam(Collection<User> users, Team team) {
        List<TeamInvite> invites = new ArrayList<>(users.size());

        for (User user : users) {
            TeamInvite teamInvite = TeamInvite.builder()
                    .teamName(team.getId())
                    .userID(user.getId())
                    .build();
            invites.add(teamInvite);
        }

        teamInviteRepository.save(invites);
    }

}
