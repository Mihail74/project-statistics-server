package ru.mdkardaev.security.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request on refresh tokens")
@Data
public class RefreshTokenRequest {

    @ApiModelProperty(value = "Refresh token", required = true)
    @NotEmpty
    private String refreshToken;
}
