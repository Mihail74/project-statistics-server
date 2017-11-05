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
     * Check invite with specified id belongs to user with specified login and return its
     */
    @Transactional
    public InviteDTO checkAccessAndGetInvite(Long inviteID, String userLogin) {
        checkInviteAndAccess(inviteID, userLogin);
        return inviteService.getInvite(inviteID);
    }


    /**
     * Check invite with specified id belongs to user with specified login and accept its
     */
    @Transactional
    public InviteDTO checkAccessAndAcceptInvite(Long inviteID, String userLogin){
        checkInviteAndAccess(inviteID, userLogin);
        return inviteService.acceptInvitation(inviteID, userLogin);
    }

    /**
     * Check invite with specified id belongs to user with specified login and accept its
     */
    @Transactional
    public InviteDTO checkAccessAndDeclineInvite(Long inviteID, String userLogin){
        checkInviteAndAccess(inviteID, userLogin);
        return inviteService.declineInvitation(inviteID);
    }

    /**
     * Check invite exist and it's invite belong to user with specified login
     */
    private void checkInviteAndAccess(Long inviteID, String userLogin) {
        inviteCheckService.checkInviteExist(inviteID);
        inviteCheckService.checkInviteBelongToUser(inviteID, userLogin);
    }
}
