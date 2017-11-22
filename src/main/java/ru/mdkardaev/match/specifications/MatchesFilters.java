package ru.mdkardaev.match.specifications;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Sort;

/**
 * Filter for Matches specification
 */
@Value
@Builder
public class MatchesFilters {

    private Long teamID;
    private Long requiredTeamMemberUserID;

    //sort order
    private SortField sortField;
    private Sort.Direction sortDirection;
}

