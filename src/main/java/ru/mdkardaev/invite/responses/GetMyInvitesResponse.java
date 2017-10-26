package ru.mdkardaev.invite.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.invite.dtos.InviteDTO;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel("Response for my invites")
public class GetMyInvitesResponse {

    @ApiModelProperty(value = "my invites", required = true)
    private List<InviteDTO> invites;
}
