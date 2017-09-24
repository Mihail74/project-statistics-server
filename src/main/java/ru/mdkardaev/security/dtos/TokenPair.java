package ru.mdkardaev.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Pair of access and refresh token
 */
@AllArgsConstructor
@Getter
public class TokenPair {

    private Token accessToken;
    private Token refreshToken;
}

