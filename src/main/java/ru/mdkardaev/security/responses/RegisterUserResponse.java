package ru.mdkardaev.security.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.user.dtos.UserDTO;

@ApiModel(value = "Response on user registration")
@Data
@AllArgsConstructor
public class RegisterUserResponse {

    @ApiModelProperty(value = "Registered user", required = true)
    private UserDTO user;
}
