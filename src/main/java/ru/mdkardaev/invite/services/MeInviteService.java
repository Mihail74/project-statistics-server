package ru.mdkardaev.invite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.invite.dtos.InviteDTO;

/**
 * Service that handlers request and check access
 */
@Service
public class MeInviteService {

    @Autowired
    private InviteService inviteService;
    @Autowired
    private InviteCheckService inviteCheckService;

    /**
     * Check invite with specified id belongs to user with specified is and return its
     */
    @Transactional
    public InviteDTO checkAccessAndGetInvite(Long inviteID, Long userID) {
        checkInviteAndAccess(inviteID, userID);
        return inviteService.getInvite(inviteID);
    }


    /**
     * Check invite with specified id belongs to user with specified id and accept its
     */
    @Transactional
    public InviteDTO checkAccessAndAcceptInvite(Long inviteID, Long userID){
        checkInviteAndAccess(inviteID, userID);
        return inviteService.acceptInvitation(inviteID, userID);
    }

    /**
     * Check invite with specified id belongs to user with specified id and accept its
     */
    @Transactional
    public InviteDTO checkAccessAndDeclineInvite(Long inviteID, Long userID){
        checkInviteAndAccess(inviteID, userID);
        return inviteService.declineInvitation(inviteID);
    }

    /**
     * Check invite exist and it's invite belong to user with specified id
     */
    private void checkInviteAndAccess(Long inviteID, Long userID) {
        inviteCheckService.checkAndGet(inviteID);
        inviteCheckService.checkInviteBelongToUser(inviteID, userID);
    }
}
