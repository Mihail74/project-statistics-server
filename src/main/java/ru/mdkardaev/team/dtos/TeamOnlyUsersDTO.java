package ru.mdkardaev.team.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.List;

@Value
@Builder
public class TeamOnlyUsersDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", example = "name", required = true)
    private String name;

    @ApiModelProperty(value = "Team members", required = true)
    private List<UserDTO> users;
}
