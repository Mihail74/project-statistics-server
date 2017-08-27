package ru.mdkardaev.player.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.player.dtos.PlayerDTO;
import ru.mdkardaev.player.entity.Player;
import ru.mdkardaev.player.exceptions.UserAlreadyExist;
import ru.mdkardaev.player.repository.PlayerRepository;
import ru.mdkardaev.player.requests.RegisterPlayerRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayerService {

    @Autowired
    private DBExceptionUtils dbExceptionUtils;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ConversionService conversionService;

    public void register(RegisterPlayerRequest request) {

        Player player = Player.builder()
                              .name(request.getName())
                              .password(request.getPassword())
                              .email(request.getEmail())
                              .build();

        try {
            playerRepository.save(player);
        } catch (DataIntegrityViolationException e) {
            dbExceptionUtils
                    .conditionThrowNewException(e,
                                                SQLStates.UNIQUE_VIOLATION,
                                                () -> new UserAlreadyExist(String.format(
                                                        "User with email: [%s] already exist.",
                                                        request.getEmail())));
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<PlayerDTO> getPlayers() {
        return playerRepository.findAll()
                               .stream()
                               .map(e -> conversionService.convert(e, PlayerDTO.class))
                               .collect(Collectors.toList());
    }
}
