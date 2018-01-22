package ru.mdkardaev.team.specifications;

import lombok.Builder;
import lombok.Value;
import ru.mdkardaev.team.enums.TeamFormingStatus;

/**
 * Filter for teams specification
 */
@Value
@Builder
public class TeamsFilters {
    private Long gameID;
    private TeamFormingStatus formingStatus;
    private Long memberID;
    private Integer pageNumber;
}
