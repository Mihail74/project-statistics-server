package ru.mdkardaev.security.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on user registration")
@Data
public class RegisterUserRequest {

    @ApiModelProperty(value = "Password", example = "password", required = true)
    private String password;

    @ApiModelProperty(value = "Login", example = "login", required = true)
    private String login;
}
