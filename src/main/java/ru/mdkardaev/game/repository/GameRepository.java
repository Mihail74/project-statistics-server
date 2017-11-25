package ru.mdkardaev.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.game.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);
}
