package ru.mdkardaev.game.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@ApiModel(value = "Request on game creation")
@Data
public class CreateGameRequest {

    @ApiModelProperty(value = "game name", example = "football", required = true)
    private String name;

    @ApiModelProperty(value = "game description", example = "table football 2x2")
    private String description;
}
