package ru.mdkardaev.team.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mdkardaev.player.entity.Player;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "team_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teams")
    private Set<Player> players;
}
