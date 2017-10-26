package ru.mdkardaev.security.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on refresh tokens")
@Data
public class RefreshTokenRequest {

    @ApiModelProperty(value = "Refresh token", required = true)
    private String refreshToken;
}
