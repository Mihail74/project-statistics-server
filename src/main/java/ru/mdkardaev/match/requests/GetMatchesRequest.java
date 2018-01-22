package ru.mdkardaev.match.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;
import ru.mdkardaev.match.specifications.SortField;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "Request list of matches")
@Data
public class GetMatchesRequest {

    @ApiModelProperty("Required participant team ID")
    private Long teamID;

    @ApiModelProperty("Flag indicates returns only matches, where user who made the request is member of participant team")
    private Boolean onlyMyMatches;


    @ApiModelProperty("Sort field")
    private SortField sortField;

    @ApiModelProperty("Sort direction")
    private Sort.Direction sortDirection;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "Page number", required = true)
    private Integer pageNumber;
}
