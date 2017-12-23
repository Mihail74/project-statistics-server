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

    @ApiModelProperty(value = "Name", example = "name", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "List of member's id", example = "[1]", required = false)
    @NotEmpty
    private List<Long> membersID;

    @ApiModelProperty(value = "Game ID", example = "1", required = true)
    @NotNull
    private Long gameID;
}
