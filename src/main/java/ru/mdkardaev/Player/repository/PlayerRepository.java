package ru.mdkardaev.Player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.Player.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
