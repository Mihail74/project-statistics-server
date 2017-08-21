package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "team1_id", foreignKey = @ForeignKey(name = "fk_team2"))
    private Team team1;

    @OneToOne
    @JoinColumn(name = "team2_id", foreignKey = @ForeignKey(name = "fk_team1"))
    private Team team2;

    @Column(name = "team1score", nullable = false)
    private Integer team1Score;

    @Column(name = "team2score", nullable = false)
    private Integer team2Score;

    @OneToOne
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game"))
    private Game game;

    @Column(name = "timestamp")
    private long timestamp;
}
