package ru.mdkardaev.security.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request on user registration")
@Data
public class RegisterUserRequest {

    @ApiModelProperty(value = "Login", example = "login", required = true)
    @NotEmpty
    private String login;

    @ApiModelProperty(value = "Password", example = "password", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "Name", example = "name", required = true)
    @NotEmpty
    private String name;
}
