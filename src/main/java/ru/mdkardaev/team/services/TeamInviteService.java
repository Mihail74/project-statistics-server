package ru.mdkardaev.team.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.entity.TeamInvite;
import ru.mdkardaev.team.enums.TeamInviteStatus;
import ru.mdkardaev.team.repository.TeamInviteRepository;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class TeamInviteService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamInviteRepository teamInviteRepository;

    @Transactional
    public void inviteUsersToTeam(Collection<User> users, Team team) {
        List<TeamInvite> invites = new ArrayList<>(users.size());

        for (User user : users) {
            TeamInvite teamInvite = TeamInvite.builder()
                    .teamID(team.getId())
                    .userID(user.getId())
                    .build();
            invites.add(teamInvite);
        }

        teamInviteRepository.save(invites);
    }

    @Transactional
    public void acceptInvitation(Long inviteID, String userLogin) {
        TeamInvite invite = teamInviteRepository.findOne(inviteID);

        if (invite == null || invite.getStatus() != null) {
            throw new InvalidParameterException("invalid parameters");
        }

        User user = userRepository.findByLogin(userLogin);
        Team team = teamRepository.findOne(invite.getTeamID());

        if (user == null || !user.getId().equals(invite.getUserID())
                || team == null) {
            throw new InvalidParameterException("invalid parameters");
        }

        team.getUsers().add(user);
        invite.setStatus(TeamInviteStatus.ACCEPTED);

        teamRepository.save(team);
        teamInviteRepository.save(invite);
    }

    @Transactional
    public void declineInvitation(Long inviteID, String userLogin) {
        TeamInvite invite = teamInviteRepository.findOne(inviteID);

        if (invite == null || invite.getStatus() != null) {
            throw new InvalidParameterException("invalid parameters");
        }

        User user = userRepository.findByLogin(userLogin);

        if (!user.getId().equals(invite.getUserID())) {
            throw new InvalidParameterException("invalid parameters");
        }

        invite.setStatus(TeamInviteStatus.DECLINED);
        
        teamInviteRepository.save(invite);
    }
}
