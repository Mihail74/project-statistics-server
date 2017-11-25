package ru.mdkardaev.invite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.common.exceptions.EntityNotFoundException;
import ru.mdkardaev.common.exceptions.NoAccessException;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.invite.repository.InviteRepository;

/**
 * Service for check invite
 */
@Service
public class InviteCheckService {

    @Autowired
    private InviteRepository inviteRepository;

    /**
     * @throws ru.mdkardaev.common.exceptions.EntityNotFoundException if invite doesn't exist
     */
    @Transactional
    public void checkInviteExist(Long id) {
        Invite invite = inviteRepository.findOne(id);

        if (invite == null) {
            throw new EntityNotFoundException("Invite not found");
        }
    }

    /**
     * Check that invite belong to user with specified id.
     *
     * @throws ru.mdkardaev.common.exceptions.NoAccessException if invite doesn't belong to user with specified login
     */
    @Transactional
    public void checkInviteBelongToUser(Long inviteID, Long userID) {
        Invite invite = inviteRepository.findOne(inviteID);
        if (!invite.getUser().getId().equals(userID)) {
            throw new NoAccessException();
        }
    }
}
