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
     * Validate token and return it's claims.
     * throws exception if token expired
     */
    public Claims validateAndGetClaims(String token) throws BadCredentialsException, JwtExpiredTokenException {
        try {
            return Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token).getBody();

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(String.format("JWT Token %s expired", token), expiredEx);
        }
    }

    /**
     * return token claims
     */
    public Claims getClaimsIncludeExpired(String token){
        try {
            return Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token).getBody();

        }  catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            return expiredEx.getClaims();
        }
    }
}
