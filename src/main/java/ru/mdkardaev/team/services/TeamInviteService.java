package ru.mdkardaev.team.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.team.dtos.TeamInviteDTO;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamInviteService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamInviteRepository teamInviteRepository;
    @Autowired
    private ConversionService conversionService;

    @Transactional
    public void inviteUsersToTeam(Collection<User> users, Team team) {
        List<TeamInvite> invites = new ArrayList<>(users.size());

        for (User user : users) {
            TeamInvite teamInvite = TeamInvite.builder()
                    .team(team)
                    .user(user)
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
        Team team = teamRepository.findOne(invite.getTeam().getId());

        if (user == null || !user.getId().equals(invite.getUser().getId())
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

        if (!user.getId().equals(invite.getUser().getId())) {
            throw new InvalidParameterException("invalid parameters");
        }

        invite.setStatus(TeamInviteStatus.DECLINED);

        teamInviteRepository.save(invite);
    }

    public List<TeamInviteDTO> getInvites(String userLogin, TeamInviteStatus status) {
        List<TeamInvite> invites = teamInviteRepository
                .findByUser_IdAndStatus(userRepository.findByLogin(userLogin).getId(), status);
        return invites.stream()
                .map(e -> conversionService.convert(e, TeamInviteDTO.class))
                .collect(Collectors.toList());
    }
}
