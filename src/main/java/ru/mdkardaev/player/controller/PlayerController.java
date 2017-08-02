package ru.mdkardaev.player.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.player.entity.Player;
import ru.mdkardaev.player.repository.PlayerRepository;
import ru.mdkardaev.team.entity.Team;
import ru.mdkardaev.team.repository.TeamRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(path = "/testPlayer",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public Player testCreate() {
        Player player = new Player();
        player.setName(UUID.randomUUID().toString());

        Team team = new Team();
        team.setName(UUID.randomUUID().toString());

        Set<Team> teams = new HashSet<>();
        teams.add(team);
        player.setTeams(teams);
        
        return playerRepository.save(player);
    }
}
