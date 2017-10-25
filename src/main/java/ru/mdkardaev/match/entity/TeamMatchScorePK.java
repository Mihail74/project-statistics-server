package ru.mdkardaev.match.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TeamMatchScorePK implements Serializable {

    @Column(name = "match_id")
    private Long matchID;

    @Column(name = "team_id")
    private Long teamID;
}
