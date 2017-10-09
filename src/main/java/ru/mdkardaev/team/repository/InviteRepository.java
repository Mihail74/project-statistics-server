package ru.mdkardaev.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.team.entity.Invite;
import ru.mdkardaev.team.enums.InviteStatus;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByUser_IdAndStatus(Long userID, InviteStatus status);
}
