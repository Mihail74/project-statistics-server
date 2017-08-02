package ru.mdkardaev.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
