package ru.mdkardaev.user.entity;

import lombok.*;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.user.roles.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "uq_users_login", columnNames = {"login"})})
@Getter
@Setter
@EqualsAndHashCode(of = {"login"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "users_id_seq")
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_users",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_users"),
            inverseForeignKey = @ForeignKey(name = "fk_team"))
    private Set<Team> teams;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(joinColumns = {@JoinColumn(name = "user_id")},
            foreignKey = @ForeignKey(name = "fk_users"))
    private Set<Role> roles;
}
