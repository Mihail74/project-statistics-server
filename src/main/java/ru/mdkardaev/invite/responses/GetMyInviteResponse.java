package ru.mdkardaev.invite.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.dtos.InviteDTO;

@Data
@AllArgsConstructor
@ApiModel("Invite container")
public class GetMyInviteResponse {

    @ApiModelProperty(value = "invite", required = true)
    private InviteDTO invite;
}
