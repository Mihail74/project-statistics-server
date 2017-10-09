package ru.mdkardaev.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.team.entity.TeamInvite;
import ru.mdkardaev.team.enums.TeamInviteStatus;

import java.util.List;

public interface TeamInviteRepository extends JpaRepository<TeamInvite, Long> {

    List<TeamInvite> findByUser_IdAndStatus(Long userID, TeamInviteStatus status);
}
