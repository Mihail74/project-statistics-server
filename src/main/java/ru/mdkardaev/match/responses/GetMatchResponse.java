package ru.mdkardaev.match.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.match.dtos.MatchDTO;

@ApiModel(value = "Match container")
@Value
public class GetMatchResponse {

    @ApiModelProperty(value = "Match", required = true)
    private MatchDTO match;
}
