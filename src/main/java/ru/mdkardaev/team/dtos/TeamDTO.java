package ru.mdkardaev.team.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class TeamDTO implements TeamWithoutGames, TeamWithoutUsers, TeamWIthoutUsersAndGames {

    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;
    @ApiModelProperty(value = "name", example = "football", required = true)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDTO> users;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GameDTO> games;
}
