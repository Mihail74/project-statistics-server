package ru.mdkardaev.statistic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "match_id_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "fk_team"))
    private Team team;

    private long winMatchesCount;

    private long totalMatchesCount;
}
