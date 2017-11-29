package ru.mdkardaev.invite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.exceptions.EntityNotFoundException;
import ru.mdkardaev.exceptions.NoAccessException;
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
     * @throws EntityNotFoundException if invite doesn't exist
     */
    @Transactional
    public void checkInviteExist(Long id) {
        Invite invite = inviteRepository.findOne(id);

        if (invite == null) {
            //TODO:
//            throw new EntityNotFoundException("Invite not found");
        }
    }

    /**
     * Check that invite belong to user with specified id.
     *
     * @throws NoAccessException if invite doesn't belong to user with specified login
     */
    @Transactional
    public void checkInviteBelongToUser(Long inviteID, Long userID) {
        Invite invite = inviteRepository.findOne(inviteID);
        if (!invite.getUser().getId().equals(userID)) {
            //TODO:
//            throw new NoAccessException();
        }
    }
}
