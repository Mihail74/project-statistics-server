package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "match")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Match {

    @EmbeddedId
    private MatchId matchId;

    @Column(name = "team1score", nullable = false)
    private Integer team1Score;

    @Column(name = "team2score", nullable = false)
    private Integer team2Score;
}
