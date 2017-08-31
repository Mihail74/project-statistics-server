package ru.mdkardaev.team.entity;

import lombok.*;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.user.entity.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team",
        uniqueConstraints = {@UniqueConstraint(name = "uq_team_name", columnNames = {"name"})})
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "team_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_users",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_team"),
            inverseForeignKey = @ForeignKey(name = "fk_users"))
    private Set<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_game",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_team"),
            inverseForeignKey = @ForeignKey(name = "fk_game"))
    private Set<Game> games;
}
