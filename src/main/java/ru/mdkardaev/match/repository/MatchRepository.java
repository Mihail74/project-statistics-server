package ru.mdkardaev.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mdkardaev.match.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
}
