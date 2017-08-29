package ru.mdkardaev.user.entity;

import lombok.*;
import ru.mdkardaev.team.entity.Team;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "uq_users_email", columnNames = {"email"})})
@Getter
@Setter
@EqualsAndHashCode(of = {"email"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "users_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_users",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_users"),
            inverseForeignKey = @ForeignKey(name = "fk_team"))
    private Set<Team> teams;
}
