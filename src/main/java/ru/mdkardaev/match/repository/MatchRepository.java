package ru.mdkardaev.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.match.entity.Match;
import ru.mdkardaev.match.entity.MatchId;

public interface MatchRepository extends JpaRepository<Match, MatchId> {
}
