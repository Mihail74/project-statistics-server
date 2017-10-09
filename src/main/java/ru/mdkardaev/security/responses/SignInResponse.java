package ru.mdkardaev.security.responses;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.mdkardaev.user.dtos.UserDTO;

@ApiModel(value = "SignIn response")
@Data
public class SignInResponse {

    @ApiModelProperty(value = "Access token", required = true)
    private String accessToken;

    @ApiModelProperty(value = "Access token expiredTime", required = true)
    private Long accessTokenExpiredTime;

    @ApiModelProperty(value = "Refresh token", required = true)
    private String refreshToken;

    @ApiModelProperty(value = "Refresh token expiredTime", required = true)
    private Long refreshTokenExpiredTime;

    @ApiModelProperty(value = "Signed user", required = true)
    private UserDTO user;
}
