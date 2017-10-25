package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity(name="team_match_score")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TeamMatchScore {

    @EmbeddedId
    private TeamMatchScorePK pk;

    @ManyToOne
    @MapsId("matchID")
    @JoinColumn(name = "match_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_match"))
    private Match match;

    @ManyToOne
    @MapsId("teamID")
    @JoinColumn(name = "team_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_team"))
    private Team team;

    private Long score;
}
