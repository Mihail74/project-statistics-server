package ru.mdkardaev.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@ApiModel(value = "User")
@Value
@Builder
public class UserDTO {

    @ApiModelProperty(value = "ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", example = "name", required = true)
    private String name;
}
