package ru.mdkardaev.team.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request to accept an invitation to the team")
@Data
public class AcceptTeamInviteRequest {

    @NotEmpty
    @ApiModelProperty(value = "Team name", example = "name", required = true)
    private Long inviteID;
}
