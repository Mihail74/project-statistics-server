package ru.mdkardaev.team.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mdkardaev.game.entity.Game;
import ru.mdkardaev.team.enums.TeamFormingStatus;
import ru.mdkardaev.user.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_users",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_team"),
            inverseForeignKey = @ForeignKey(name = "fk_users"))
    private Set<User> users;

    @OneToOne
    @JoinColumn(name = "leader_id", foreignKey = @ForeignKey(name = "fk_leader_user"))
    private User leader;

    @OneToOne
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game"))
    private Game game;

    @Enumerated(EnumType.STRING)
    private TeamFormingStatus formingStatus;

    private Long numberOfMatches = 0L;

    private Long numberOfWinMatches = 0L;
}
