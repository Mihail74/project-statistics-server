package ru.mdkardaev.match.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TeamMatchScorePK implements Serializable {

    @Column(name = "match_id")
    private Long matchID;

    @Column(name = "team_id")
    private Long teamID;
}
