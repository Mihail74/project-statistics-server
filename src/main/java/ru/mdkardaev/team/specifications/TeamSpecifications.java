package ru.mdkardaev.team.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.user.entity.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * Factory class for team specifications
 */
@UtilityClass
public class TeamSpecifications {

    /**
     * Create specification for list of teams that match specified filters
     */
    public static Specification<Team> createGetTeamsSpecification(TeamsFilters filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getGameID() != null) {
                predicates.add(cb.equal(root.get("game").get("id"), filters.getGameID()));
            }
            if (filters.getFormingStatus() != null) {
                predicates.add(cb.equal(root.get("formingStatus"), filters.getFormingStatus()));
            }
            if (filters.getMemberUserLogin() != null) {
                Join<Team, User> users = root.join("users");
                predicates.add(cb.equal(users.get("login"), filters.getMemberUserLogin()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
