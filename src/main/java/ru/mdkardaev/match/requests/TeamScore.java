package ru.mdkardaev.match.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Team id and their score")
public class TeamScore {

    @ApiModelProperty(value = "Team ID", required = true)
    @NotNull
    private Long teamID;

    @ApiModelProperty(value = "Participating teams 1 id", required = true)
    @NotNull
    private Long score;
}
