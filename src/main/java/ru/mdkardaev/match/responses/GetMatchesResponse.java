package ru.mdkardaev.match.responses;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.match.dtos.MatchDTO;
import ru.mdkardaev.match.dtos.MatchesPage;

import java.util.List;

@ApiModel(value = "Matches container")
@Value
public class GetMatchesResponse {

    @ApiModelProperty("Matches page")
    private MatchesPage page;
}
