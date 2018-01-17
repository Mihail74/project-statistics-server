package ru.mdkardaev.team.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request for list of team")
@Data
public class EditTeamRequest {

    @ApiModelProperty(value = "Name", required = true)
    @NotEmpty
    private String name;
}
