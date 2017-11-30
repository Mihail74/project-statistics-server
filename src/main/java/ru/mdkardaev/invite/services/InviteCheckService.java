package ru.mdkardaev.invite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.exceptions.EntityNotFoundException;
import ru.mdkardaev.exceptions.NoAccessException;
import ru.mdkardaev.i18n.services.Messages;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.invite.repository.InviteRepository;

/**
 * Service for check invite
 */
@Service
public class InviteCheckService {

    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private Messages messages;

    /**
     * @throws EntityNotFoundException if invite doesn't exist
     */
    public Invite checkAndGet(Long id) {
        Invite invite = inviteRepository.findOne(id);

        if (invite == null) {
            throw new EntityNotFoundException(messages.getMessage("invite.errors.notFound", id));
        }
        return invite;
    }

    /**
     * Check that invite belong to user with specified id.
     *
     * @throws NoAccessException if invite doesn't belong to user with specified login
     */
    public void checkInviteBelongToUser(Long inviteID, Long userID) {
        Invite invite = checkAndGet(inviteID);
        if (!invite.getUser().getId().equals(userID)) {
            throw new NoAccessException(messages.getMessage("invite.errors.inviteForOtherUser"));
        }
    }
}
