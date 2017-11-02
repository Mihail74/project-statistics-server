package ru.mdkardaev.match.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.mdkardaev.match.entity.Match;
import ru.mdkardaev.match.entity.TeamMatchScore;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for match specifications
 */
@UtilityClass
public class MatchSpecifications {

    /**
     * Create specification for list of matches that match specified filters
     */
    public static Specification<Match> createGetMatchesSpecification(MatchesFilters filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getTeamID() != null) {
                Join<Match, TeamMatchScore> teamsMatchScore = root.join("teamsMatchScore");
                predicates.add(cb.equal(teamsMatchScore.get("pk").get("teamID"), filters.getTeamID()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * create Sort by specified filters
     */
    public static Sort createGetMatchesSort(MatchesFilters filters) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, SortField.TIMESTAMP.getProperty());

        if (filters.getSortField() != null && filters.getSortDirection() != null) {
            order = new Sort.Order(filters.getSortDirection(),
                                   filters.getSortField().getProperty());
        }
        return new Sort(order);
    }


}
