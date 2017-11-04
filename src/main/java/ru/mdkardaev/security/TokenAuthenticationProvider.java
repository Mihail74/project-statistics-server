package ru.mdkardaev.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import ru.mdkardaev.security.jwt.JwtConstants;
import ru.mdkardaev.security.jwt.JwtValidator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link AuthenticationProvider} implementation
 * that will use provided instance of Authentication to perform authentication.
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtValidator jwtValidator;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String rawAccessToken = (String) authentication.getCredentials();

        Claims claims = jwtValidator.validateAndGetClaims(rawAccessToken);

        String subject = claims.getSubject();
        List<String> roles = (List<String>) claims.get(JwtConstants.USER_ROLES, List.class);

        List<GrantedAuthority> authorities = roles.stream()
                                                  .map(SimpleGrantedAuthority::new)
                                                  .collect(Collectors.toList());

        User userDetails = new User(subject, "******", authorities);
        userDetails.eraseCredentials();

        return new AuthenticationToken(rawAccessToken, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
