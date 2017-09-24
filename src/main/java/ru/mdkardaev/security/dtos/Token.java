package ru.mdkardaev.security.dtos;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class represents raw JWS and it's claims
 */
@AllArgsConstructor
@Getter
public class Token {

    private String rawToken;
    private Claims claims;
}
