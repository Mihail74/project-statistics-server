package ru.mdkardaev.team.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "Invite to team")
@Data
@Builder
@AllArgsConstructor
public class TeamInviteDTO {

}
