package ru.mdkardaev.invite.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "Request to accept an invitation to the team")
@Data
public class AcceptInviteRequest {

    @NotNull
    @ApiModelProperty(value = "Invite id", example = "1", required = true)
    private Long id;
}
