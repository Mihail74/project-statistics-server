package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class MatchId implements Serializable {

    @OneToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @OneToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    @OneToOne
    private Game game;

    @Column(name = "timestamp")
    private long timestamp;
}
