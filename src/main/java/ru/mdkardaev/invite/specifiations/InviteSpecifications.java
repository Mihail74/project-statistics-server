package ru.mdkardaev.invite.specifiations;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mdkardaev.invite.entity.Invite;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for invite specifications
 */
@UtilityClass
public class InviteSpecifications {
    /**
     * Create specification for list of invites that match specified filters
     */
    public static Specification<Invite> createGetInviteSpecification(InvitesFilter filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("id"), filters.getUserID()));

            if (filters.getInviteStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filters.getInviteStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
