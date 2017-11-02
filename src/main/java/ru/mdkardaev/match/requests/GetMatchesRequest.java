package ru.mdkardaev.match.requests;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.domain.Sort;
import ru.mdkardaev.match.specifications.SortField;

import java.util.List;

@ApiModel(value = "Request list of matches")
@Data
public class GetMatchesRequest {

    private Long teamID;

    private SortField sortField;
    private Sort.Direction sortDirection;
}
