package ru.mdkardaev.invite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.invite.enums.InviteStatus;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByUser_LoginAndStatus(String login, InviteStatus status);

    List<Invite> findByTeam_id(Long teamID);
}
