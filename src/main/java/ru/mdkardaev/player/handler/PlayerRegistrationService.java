package ru.mdkardaev.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdkardaev.player.entity.Player;
import ru.mdkardaev.player.repository.PlayerRepository;
import ru.mdkardaev.player.requests.RegisterRequest;

@Service
public class PlayerRegistrationService {

    @Autowired
    private PlayerRepository playerRepository;

    public Long register(RegisterRequest request) {

        Player player = Player.builder()
                              .name(request.getName())
                              .password(request.getPassword())
                              .email(request.getEmail())
                              .build();

        return playerRepository.save(player).getId();

    }
}
