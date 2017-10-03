package ru.mdkardaev.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.team.entity.TeamInvite;

public interface TeamInviteRepository extends JpaRepository<TeamInvite, Long> {
}
