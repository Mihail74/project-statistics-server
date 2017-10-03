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

    @ApiModelProperty(value = "List of member's login", example = "[login1]")
    private List<String> memberLogins;

    @NotNull
    @ApiModelProperty(value = "Game name", example = "football", required = true)
    private String gameName;
}
