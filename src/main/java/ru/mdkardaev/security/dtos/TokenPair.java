package ru.mdkardaev.security.dtos;

import lombok.Value;

/**
 * Pair of access and refresh token
 */
@Value
public class TokenPair {

    private Token accessToken;
    private Token refreshToken;
}

