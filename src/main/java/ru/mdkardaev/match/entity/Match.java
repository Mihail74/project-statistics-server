package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.*;
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

    @OneToMany
    @JoinTable(name = "team_match",
            joinColumns = {@JoinColumn(name = "match_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_match_id"))},
            inverseJoinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_team_id"))})
    private Set<Team> teams;

    @OneToOne
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "fk_team_winner"))
    private Team winner;

    @OneToOne
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game"))
    private Game game;

    @Column(name = "timestamp")
    private long timestamp;
}
