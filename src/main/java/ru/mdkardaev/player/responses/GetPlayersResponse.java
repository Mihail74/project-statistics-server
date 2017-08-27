package ru.mdkardaev.player.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.player.dtos.PlayerDTO;

import java.util.List;

@ApiModel(value = "Response for players list")
@Data
@AllArgsConstructor
public class GetPlayersResponse {

    @ApiModelProperty(value = "Players list", required = true)
    private List<PlayerDTO> players;
}
