package ru.mdkardaev.team.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import java.util.List;

@ApiModel(value = "Request on team creation")
@Data
public class CreateTeamRequest {

    @NotEmpty
    @ApiModelProperty(value = "Team name", example = "name", required = true)
    private String name;

    @ApiModelProperty(value = "List of member's id", example = "[1]")
    private List<Long> membersID;

    @NotNull
    @ApiModelProperty(value = "Game id", example = "1", required = true)
    private Long gameID;
}
