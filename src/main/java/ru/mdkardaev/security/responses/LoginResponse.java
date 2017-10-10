package ru.mdkardaev.security.responses;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mdkardaev.user.dtos.UserDTO;

@ApiModel(value = "Login response")
@Data
@AllArgsConstructor
public class LoginResponse {

    @ApiModelProperty(value = "Access token", required = true)
    private String accessToken;

    @ApiModelProperty(value = "Refresh token", required = true)
    private String refreshToken;

    @ApiModelProperty(value = "Signed user", required = true)
    private UserDTO user;
}
