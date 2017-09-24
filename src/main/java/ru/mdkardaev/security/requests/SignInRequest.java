package ru.mdkardaev.security.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SignIn request")
@Data
public class SignInRequest {

    @ApiModelProperty(value = "Login", example = "login", required = true)
    private String login;

    @ApiModelProperty(value = "Password", example = "password", required = true)
    private String password;
}
