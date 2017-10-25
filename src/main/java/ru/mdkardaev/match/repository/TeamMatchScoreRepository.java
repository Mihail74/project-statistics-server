package ru.mdkardaev.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.match.entity.TeamMatchScore;
import ru.mdkardaev.match.entity.TeamMatchScorePK;

public interface TeamMatchScoreRepository extends JpaRepository<TeamMatchScore, TeamMatchScorePK> {
}
