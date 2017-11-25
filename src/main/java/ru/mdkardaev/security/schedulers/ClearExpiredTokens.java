package ru.mdkardaev.security.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.user.repository.TokenRepository;

import java.time.Instant;

@Slf4j
@Component
public class ClearExpiredTokens {

    @Autowired
    private TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void clearExpiredTokens() {
        tokenRepository.deleteExpiredTokens(Instant.now().toEpochMilli());
        log.info("Expired tokens have been removed");
    }
}
