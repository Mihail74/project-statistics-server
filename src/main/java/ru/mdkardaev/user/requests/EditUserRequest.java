package ru.mdkardaev.user.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Request to edit user")
@Data
public class EditUserRequest {

    @NotEmpty
    @ApiModelProperty(value = "New name", required = true)
    private String name;
}
