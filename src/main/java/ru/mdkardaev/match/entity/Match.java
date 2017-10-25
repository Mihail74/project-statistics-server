package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "match")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "match_id_seq")
    private Long id;

    @OneToMany(mappedBy = "pk.matchID")
    private Set<TeamMatchScore> teamsMatchScore;

    @OneToOne
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "fk_team_winner"))
    private Team winner;

    @OneToOne
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game"))
    private Game game;

    private long timestamp;
}
