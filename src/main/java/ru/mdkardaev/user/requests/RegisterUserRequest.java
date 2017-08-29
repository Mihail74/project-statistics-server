package ru.mdkardaev.user.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@ApiModel(value = "Request on user registration")
@Data
public class RegisterUserRequest {

    @ApiModelProperty(value = "user name", example = "Ivan", required = true)
    private String name;
    @ApiModelProperty(value = "Password", required = true)
    private String password;
    @ApiModelProperty(value = "email (will be used as login)", example = "test@gmail.com", required = true)
    @Email
    private String email;
}
