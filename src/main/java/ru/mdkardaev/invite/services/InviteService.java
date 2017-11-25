package ru.mdkardaev.invite.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.EntityNotFoundException;
import ru.mdkardaev.common.exceptions.InvalidParameterException;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.invite.exceptions.WrongInviteStatusException;
import ru.mdkardaev.invite.repository.InviteRepository;
import ru.mdkardaev.invite.specifiations.InviteSpecifications;
import ru.mdkardaev.invite.specifiations.InvitesFilter;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;
import ru.mdkardaev.team.services.TeamCheckService;
import ru.mdkardaev.team.specifications.TeamSpecifications;
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
    @Autowired
    private TeamCheckService teamCheckService;

    /**
     * Create invites in team for specified users
     *
     * @return created invites
     */
    @Transactional
    public List<InviteDTO> inviteUsersToTeam(Collection<Long> userIDs, Long teamID) {
        List<Invite> invites = new ArrayList<>(CollectionUtils.size(userIDs));

        Team team = teamCheckService.checkAndGetTeam(teamID);
        List<User> users = userRepository.findAll(userIDs);

        for (User user : users) {
            Invite invite = Invite.builder()
                                  .team(team)
                                  .user(user)
                                  .status(InviteStatus.NEW)
                                  .build();
            invites.add(invite);
        }

        return inviteRepository.save(invites)
                               .stream()
                               .map(e -> conversionService.convert(e, InviteDTO.class))
                               .collect(Collectors.toList());
    }

    /**
     * Accept invite and return updated
     */
    @Transactional
    public InviteDTO acceptInvitation(Long inviteID, Long userID) {
        Invite invite = inviteRepository.findOne(inviteID);

        if (invite.getStatus() != InviteStatus.NEW) {
            throw new WrongInviteStatusException("Invite already accepted/declined");
        }

        User user = userRepository.findOne(userID);
        Team team = teamRepository.findOne(invite.getTeam().getId());

        if (team == null) {
            throw new InvalidParameterException("teamID", "Team doesn't exist");
        }

        team.getUsers().add(user);
        teamRepository.save(team);

        invite.setStatus(InviteStatus.ACCEPTED);
        Invite savedInvite = inviteRepository.save(invite);

        return conversionService.convert(savedInvite, InviteDTO.class);
    }

    /**
     * Decline invite and return updated
     */
    @Transactional
    public InviteDTO declineInvitation(Long inviteID) {
        Invite invite = inviteRepository.findOne(inviteID);

        if (invite.getStatus() != InviteStatus.NEW) {
            throw new WrongInviteStatusException("Invite already accepted/declined");
        }

        invite.setStatus(InviteStatus.DECLINED);

        Invite savedInvite = inviteRepository.save(invite);
        return conversionService.convert(savedInvite, InviteDTO.class);
    }

    /**
     * @return invites for specified filter
     */
    public List<InviteDTO> getUserInvites(InvitesFilter filters) {
        Specification<Invite> specification = InviteSpecifications.createGetInviteSpecification(filters);
        return inviteRepository.findAll(specification)
                             .stream()
                             .map(e -> conversionService.convert(e, InviteDTO.class))
                             .collect(Collectors.toList());
    }

    /**
     * @return invite with specified id
     */
    public InviteDTO getInvite(Long id) {
        return conversionService.convert(inviteRepository.findOne(id), InviteDTO.class);
    }

    /**
     * Delete all invites to team with specified id
     */
    public void deleteInvitesInTeam(Long id) {
        List<Invite> invites = inviteRepository.findByTeam_id(id);
        inviteRepository.delete(invites);
    }

    /**
     * Return invites in team
     */
    @Transactional
    public List<InviteDTO> getInvitesInTeam(Long id) {
        teamCheckService.checkAndGetTeam(id);

        return inviteRepository.findByTeam_id(id)
                               .stream()
                               .map(e -> conversionService.convert(e, InviteDTO.class))
                               .collect(Collectors.toList());
    }
}
