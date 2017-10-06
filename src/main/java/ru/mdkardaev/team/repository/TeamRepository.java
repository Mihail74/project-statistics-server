package ru.mdkardaev.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.enums.TeamFormingStatus;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);

    List<Team> findByUsers_login(String login);

    List<Team> findByUsers_loginAndFormingStatus(String login, TeamFormingStatus formingStatus);
}
