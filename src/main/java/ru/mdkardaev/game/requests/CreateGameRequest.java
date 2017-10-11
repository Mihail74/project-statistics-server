package ru.mdkardaev.game.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request on game creation")
@Data
public class CreateGameRequest {

    @ApiModelProperty(value = "game name", example = "football", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "game description", example = "table football 2x2")
    private String description;
}
