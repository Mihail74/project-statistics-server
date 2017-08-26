package ru.mdkardaev.player.handler;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.player.entity.Player;
import ru.mdkardaev.player.exceptions.UserAlreadyExist;
import ru.mdkardaev.player.repository.PlayerRepository;
import ru.mdkardaev.player.requests.RegisterRequest;

@Service
@Slf4j
public class PlayerRegistrationService {

    @Autowired
    private PlayerRepository playerRepository;

    public void register(RegisterRequest request) {

        Player player = Player.builder()
                              .name(request.getName())
                              .password(request.getPassword())
                              .email(request.getEmail())
                              .build();

        try {
            playerRepository.save(player);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                String sqlState = ((ConstraintViolationException) e.getCause()).getSQLException().getSQLState();
                if (SQLStates.UNIQUE_VIOLATION == SQLStates.valueByCode(sqlState)) {
                    throw new UserAlreadyExist(String.format("User with email: [%s] already exist.",
                                                             request.getEmail()));
                }
            }
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
