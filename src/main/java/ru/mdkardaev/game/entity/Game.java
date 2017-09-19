package ru.mdkardaev.game.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "game",
        uniqueConstraints = {@UniqueConstraint(name = "uq_game_name", columnNames = {"name"})})
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "game_id_seq")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

}
