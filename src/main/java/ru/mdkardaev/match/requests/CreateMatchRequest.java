package ru.mdkardaev.match.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import java.util.Set;

@ApiModel(value = "Request on match creation")
@Value
public class CreateMatchRequest {

    @ApiModelProperty(value = "Participating teams and their scores", required = true)
    @NotEmpty
    private Set<TeamScore> teamsScore;

    @ApiModelProperty(value = "Game ID", required = true)
    @NotNull
    private Long gameID;

    @ApiModelProperty(value = "The time of the match", required = true)
    @NotNull
    private Long timestamp;
}
