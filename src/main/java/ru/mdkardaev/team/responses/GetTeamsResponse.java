package ru.mdkardaev.team.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.team.dtos.TeamsPage;

import java.util.List;

@ApiModel(value = "Team list")
@Data
@AllArgsConstructor
public class GetTeamsResponse {

    @ApiModelProperty("Teams page")
    private TeamsPage page;
}
