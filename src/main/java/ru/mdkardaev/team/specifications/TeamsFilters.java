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
    private final Long gameID;
    private final TeamFormingStatus formingStatus;
    private final Long memberID;
}
