package ru.mdkardaev.match.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;
import ru.mdkardaev.match.specifications.SortField;

import java.util.List;

@ApiModel(value = "Request list of matches")
@Data
public class GetMatchesRequest {

    @ApiModelProperty("Required participant team ID")
    private Long teamID;


    @ApiModelProperty("Sort field")
    private SortField sortField;

    @ApiModelProperty("Sort direction")
    private Sort.Direction sortDirection;
}
