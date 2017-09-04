package ru.mdkardaev.team.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.mdkardaev.game.dtos.GameDTO;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.List;

@ApiModel(value = "Team")
@Data
@Builder
@AllArgsConstructor
public class TeamDTO {

    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;
    @ApiModelProperty(value = "name", example = "football", required = true)
    private String name;
    private List<UserDTO> users;
    private List<GameDTO> games;
}
