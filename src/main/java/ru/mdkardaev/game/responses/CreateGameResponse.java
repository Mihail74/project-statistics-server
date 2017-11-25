package ru.mdkardaev.game.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.game.dtos.GameDTO;

@ApiModel(value = "Response for create game")
@Data
@AllArgsConstructor
public class CreateGameResponse {

    @ApiModelProperty(value = "Created game", required = true)
    private GameDTO game;
}
