package ru.mdkardaev.game.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.game.dtos.GameDTO;

import java.util.List;

@ApiModel(value = "Response for games list")
@Data
@AllArgsConstructor
public class GetGamesResponse {

    @ApiModelProperty(value = "Games list", required = true)
    private List<GameDTO> games;
}
