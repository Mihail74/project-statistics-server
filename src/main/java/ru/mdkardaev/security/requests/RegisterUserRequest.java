package ru.mdkardaev.security.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request on user registration")
@Data
public class RegisterUserRequest {

    @NotEmpty
    @ApiModelProperty(value = "Password", example = "password", required = true)
    private String password;

    @NotEmpty
    @ApiModelProperty(value = "Login", example = "login", required = true)
    private String login;

    @NotEmpty
    @ApiModelProperty(value = "Name", example = "name", required = true)
    private String name;
}
