package ru.mdkardaev.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtSettings {
    /**
     * JWT will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;

    /**
     * Key is used to sign JwtHolder.
     */
    private String tokenSigningKey;

    /**
     * JWT can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpirationTime;
}
