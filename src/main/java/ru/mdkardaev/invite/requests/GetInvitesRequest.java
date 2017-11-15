package ru.mdkardaev.invite.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.mdkardaev.invite.enums.InviteStatus;

@ApiModel(value = "Request for list of invites")
@Data
public class GetInvitesRequest {

    @ApiModelProperty(value = "Invite status")
    private InviteStatus status;
}
