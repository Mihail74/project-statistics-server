package ru.mdkardaev.user.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import ru.mdkardaev.user.dtos.UserDTO;

import java.util.List;

@ApiModel(value = "Response to edit user")
@Value
public class EditUserResponse {

    @ApiModelProperty(value = "User", required = true)
    private UserDTO user;
}
