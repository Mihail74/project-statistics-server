package ru.mdkardaev.team.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "team_invites")
@Getter
@Setter
@EqualsAndHashCode(of = {"userLogin", "teamName"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", sequenceName = "team_invite_id_seq")
    private Long id;

    private Long userID;

    private Long teamName;
}
