package ru.mdkardaev.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtHolder.
 */
public class AuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2877954820905567501L;

    private final String rawAccessToken;
    private final UserDetails userDetails;

    /**
     * Constructor for <b>not authenticated</b> token.
     */
    public AuthenticationToken(String rawAccessToken) {
        this(rawAccessToken, null);
    }

    /**
     * Constructor for <b>authenticated</b> token.
     */
    public AuthenticationToken(String rawAccessToken, UserDetails userDetails) {
        super(userDetails != null ? userDetails.getAuthorities() : null);

        this.userDetails = userDetails;
        this.rawAccessToken = rawAccessToken;
        setAuthenticated(userDetails != null);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }
}
