package ru.mdkardaev.security.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value = "Refresh token response")
@Data
@AllArgsConstructor
public class RefreshTokenResponse {

    @ApiModelProperty(value = "Access token", required = true)
    private String accessToken;

    @ApiModelProperty(value = "Refresh token", required = true)
    private String refreshToken;
}
