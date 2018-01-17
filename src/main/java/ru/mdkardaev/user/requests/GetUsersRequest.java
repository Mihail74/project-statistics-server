package ru.mdkardaev.user.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request for a list of users")
@Data
public class GetUsersRequest {

    @ApiModelProperty("Включить ли запрашивающего пользователя в результирующий список")
    private Boolean includeMe;
}
