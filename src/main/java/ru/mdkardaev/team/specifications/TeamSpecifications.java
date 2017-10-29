package ru.mdkardaev.team.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;


@UtilityClass
public class TeamSpecifications {

    public static Specification<Team> gameID(Long gameID) {
        return (root, query, cb) -> cb.equal(root.get("game").get("id"), gameID);
    }

    public static Specification<Team> formingStatus(TeamFormingStatus formingStatus) {
        return (root, query, cb) -> cb.equal(root.get("formingStatus"), formingStatus);
    }
}
