package ru.mdkardaev.invite.specifiations;

import lombok.Builder;
import lombok.Value;
import ru.mdkardaev.invite.enums.InviteStatus;

/**
 * Filter for invites specification
 */
@Value
@Builder
public class InvitesFilter {

    private Long userID;
    private InviteStatus inviteStatus;
}
