package ru.mdkardaev.user.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.List;

@ApiModel(value = "Response for users list")
@Data
@AllArgsConstructor
public class GetUsersResponse {

    @ApiModelProperty(value = "Users list", required = true)
    private List<UserDTO> users;
}
