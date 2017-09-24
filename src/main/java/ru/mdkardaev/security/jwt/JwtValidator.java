package ru.mdkardaev.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mdkardaev.security.config.JwtSettings;
import ru.mdkardaev.security.exceptions.BadCredentialsException;
import ru.mdkardaev.security.exceptions.JwtExpiredTokenException;

@Slf4j
@Component
public class JwtValidator {

    @Autowired
    private JwtSettings jwtSettings;

    /**
     * Validate token and return it's claims
     */
    public Jws<Claims> validateAndGetClaims(String token) throws BadCredentialsException, JwtExpiredTokenException {
        //TODO: здесь нужено также включить проверку на неотозванность токена
        // (как вариант при отзыве токена иметь какой-то глобальный кэш, чтобы не кидать запросы в БД)
        // Отзыв возможен либо при рефреше, выходе либо программно
        try {
            return Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token);

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(String.format("JWT Token %s expired", token), expiredEx);
        }
    }
}
