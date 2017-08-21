package ru.mdkardaev.player.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on player registration")
@Data
public class RegisterRequest {

    @ApiModelProperty(value = "email (will be used as login)", example = "test@gmail.com", required = true)
    private String email;
}
