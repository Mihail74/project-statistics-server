package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import ru.mdkardaev.team.dtos.TeamDTO;

import java.util.List;

@ApiModel(value = "User's teams")
@Value
public class GetMyTeamsResponse {

    @ApiModelProperty(value = "Teams", required = true)
    private List<TeamDTO> teams;
}
