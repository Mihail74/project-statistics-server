package ru.mdkardaev.Player.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.Player.entity.Player;
import ru.mdkardaev.Player.repository.PlayerRepository;

import java.util.UUID;

@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(path = "/testPlayer",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public Player testCreate() {
        Player player = new Player();
        player.setName(UUID.randomUUID().toString());
        return playerRepository.save(player);
    }
}
