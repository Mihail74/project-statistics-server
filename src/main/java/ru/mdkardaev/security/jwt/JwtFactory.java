package ru.mdkardaev.security.jwt;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mdkardaev.security.config.JwtSettings;
import ru.mdkardaev.security.dtos.Token;
import ru.mdkardaev.security.dtos.TokenPair;
import ru.mdkardaev.security.dtos.TokenType;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.roles.Role;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Factory for JWT
 */
@Component
public class JwtFactory {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    @Autowired
    private JwtSettings settings;

    /**
     * Factory method for issuing token pair (access and refresh)
     */
    public TokenPair createTokenPair(User user) {
        Token accessToken = createAccessToken(user);
        Token refreshToken = createRefreshToken(user, accessToken.getClaims().getId());
        return new TokenPair(accessToken, refreshToken);
    }

    /**
     * Factory method for issuing new Access-JWT.
     */
    private Token createAccessToken(User user) {
        return createToken(user, TokenType.ACCESS_TOKEN, null);
    }

    /**
     * Factory method for issuing new Refresh-JWT.
     */
    private Token createRefreshToken(User user, String connectedAccessTokenId) {
        return createToken(user, TokenType.REFRESH_TOKEN, connectedAccessTokenId);
    }

    private Token createToken(User user, TokenType tokenType, String connectedAccessTokenId) {
        LocalDateTime currentTime = LocalDateTime.now();

        Instant expirationTime = currentTime
                .plusMinutes(tokenType == TokenType.ACCESS_TOKEN
                                     ? settings.getTokenExpirationTime()
                                     : settings.getRefreshTokenExpirationTime())
                .atZone(ZoneId.systemDefault()).toInstant();


        Claims claims = Jwts.claims()
                            .setId(UUID.randomUUID().toString())
                            .setSubject(user.getLogin())
                            .setIssuer(settings.getTokenIssuer())
                            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                            .setExpiration(Date.from(expirationTime));

        claims.put(JwtConstants.USER_ROLES,
                   user.getRoles().stream().map(Role::name).collect(Collectors.toList()));
        claims.put(JwtConstants.TOKEN_TYPE, tokenType);
        claims.put(JwtConstants.CONNECTED_TOKEN, connectedAccessTokenId);


        JwsHeader header = Jwts.jwsHeader().setAlgorithm(SIGNATURE_ALGORITHM.getValue());
        header.setType(Header.JWT_TYPE);

        String token = Jwts.builder()
                           .setHeader((Map<String, Object>) header)
                           .setClaims(claims)
                           .signWith(SIGNATURE_ALGORITHM, settings.getTokenSigningKey())
                           .compact();

        return new Token(token, claims);
    }
}
