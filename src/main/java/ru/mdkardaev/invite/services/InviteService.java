package ru.mdkardaev.invite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.dtos.InvitedUserDTO;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.invite.repository.InviteRepository;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.user.dtos.UserDTO;
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
                    .status(InviteStatus.NEW)
                    .build();
            invites.add(invite);
        }

        inviteRepository.save(invites);
    }

    @Transactional
    public void acceptInvitation(Long inviteID, String userLogin) {
        Invite invite = inviteRepository.findOne(inviteID);

        if (invite == null || invite.getStatus() != InviteStatus.NEW) {
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

    public InviteDTO getInvite(Long id) {
        return conversionService.convert(inviteRepository.findOne(id), InviteDTO.class);
    }

    /**
     * delete all invites to team with specified id
     */
    public void deleteInvites(Long id) {
        List<Invite> invites = inviteRepository.findByTeam_id(id);
        inviteRepository.delete(invites);
    }

    public List<InvitedUserDTO> getUsersInvitedInTeam(Long id) {
        return inviteRepository.findByTeam_id(id).stream().map(e -> {
            UserDTO user = conversionService.convert(e.getUser(), UserDTO.class);
            return new InvitedUserDTO(user, e.getStatus());
        }).collect(Collectors.toList());
    }
}
