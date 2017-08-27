package ru.mdkardaev.player.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Request on players list")
@Data
public class GetPlayersRequest {

    @ApiModelProperty(value = "Stub. Just for testing", example = "test")
    private String test;
}
