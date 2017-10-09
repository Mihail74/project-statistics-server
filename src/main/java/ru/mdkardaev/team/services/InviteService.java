package ru.mdkardaev.team.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.team.dtos.InviteDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.entity.Invite;
import ru.mdkardaev.team.enums.InviteStatus;
import ru.mdkardaev.team.repository.InviteRepository;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InviteService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private ConversionService conversionService;

    @Transactional
    public void inviteUsersToTeam(Collection<User> users, Team team) {
        List<Invite> invites = new ArrayList<>(users.size());

        for (User user : users) {
                Invite invite = Invite.builder()
                                      .team(team)
                                      .user(user)
                                      .build();
            invites.add(invite);
        }

        inviteRepository.save(invites);
    }

    @Transactional
    public void acceptInvitation(Long inviteID, String userLogin) {
        Invite invite = inviteRepository.findOne(inviteID);

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
        invite.setStatus(InviteStatus.ACCEPTED);

        teamRepository.save(team);
        inviteRepository.save(invite);
    }

    @Transactional
    public void declineInvitation(Long inviteID, String userLogin) {
        Invite invite = inviteRepository.findOne(inviteID);

        if (invite == null || invite.getStatus() != null) {
            throw new InvalidParameterException("invalid parameters");
        }

        User user = userRepository.findByLogin(userLogin);

        if (!user.getId().equals(invite.getUser().getId())) {
            throw new InvalidParameterException("invalid parameters");
        }

        invite.setStatus(InviteStatus.DECLINED);

        inviteRepository.save(invite);
    }

    public List<InviteDTO> getUserInvites(String userLogin, InviteStatus status) {
        List<Invite> invites = inviteRepository
                .findByUser_IdAndStatus(userRepository.findByLogin(userLogin).getId(), status);
        return invites.stream()
                .map(e -> conversionService.convert(e, InviteDTO.class))
                .collect(Collectors.toList());
    }

}