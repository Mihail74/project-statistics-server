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

    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;
    @ApiModelProperty(value = "name", example = "Ivan", required = true)
    private String name;
    @ApiModelProperty(value = "email", example = "test@gmail.com", required = true)
    private String email;
}
