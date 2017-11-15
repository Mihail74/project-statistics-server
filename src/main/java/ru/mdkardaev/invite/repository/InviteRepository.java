package ru.mdkardaev.invite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.invite.enums.InviteStatus;
import ru.mdkardaev.team.entity.Team;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long>, JpaSpecificationExecutor<Invite> {

    List<Invite> findByTeam_id(Long teamID);
}
