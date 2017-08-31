package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.team.dtos.TeamDTO;

import java.util.List;

@ApiModel(value = "Response for users list")
@Data
@AllArgsConstructor
public class GetTeamResponse {
    @ApiModelProperty(value = "Users list", required = true)
    private List<TeamDTO> teams;
}
