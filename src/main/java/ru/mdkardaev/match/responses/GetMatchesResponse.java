package ru.mdkardaev.match.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.match.dtos.MatchDTO;

import java.util.List;

@ApiModel(value = "Matches container")
@Value
public class GetMatchesResponse {

    @ApiModelProperty(value = "Matches", required = true)
    private List<MatchDTO> matches;
}
