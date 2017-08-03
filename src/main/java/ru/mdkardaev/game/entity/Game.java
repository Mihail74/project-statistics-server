package ru.mdkardaev.game.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "game",
        uniqueConstraints = {@UniqueConstraint(name = "uq_game_name", columnNames = {"name"})})
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "game_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "scoreToWin")
    private Integer scoreToWin;
}
