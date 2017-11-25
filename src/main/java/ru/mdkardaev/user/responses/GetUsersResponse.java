package ru.mdkardaev.user.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.List;

@ApiModel(value = "Response for users list")
@Value
public class GetUsersResponse {

    @ApiModelProperty(value = "List of Users", required = true)
    private List<UserDTO> users;
}
