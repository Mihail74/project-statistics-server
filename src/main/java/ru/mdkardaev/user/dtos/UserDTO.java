package ru.mdkardaev.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "User")
@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    @ApiModelProperty(value = "login", example = "login", required = true)
    private String login;
}
