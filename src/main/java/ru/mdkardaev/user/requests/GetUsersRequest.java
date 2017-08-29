package ru.mdkardaev.user.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on users list")
@Data
public class GetUsersRequest {

    @ApiModelProperty(value = "Stub. Just for testing", example = "test")
    private String test;
}
