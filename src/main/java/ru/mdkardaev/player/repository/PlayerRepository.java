package ru.mdkardaev.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.player.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
