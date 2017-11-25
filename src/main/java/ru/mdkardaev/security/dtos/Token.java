package ru.mdkardaev.security.dtos;

import io.jsonwebtoken.Claims;
import lombok.Value;

/**
 * Class represents raw JWS and it's claims
 */
@Value
public class Token {

    private String rawToken;
    private Claims claims;
}
