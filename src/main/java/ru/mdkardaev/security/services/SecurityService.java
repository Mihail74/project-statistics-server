package ru.mdkardaev.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.security.dtos.Token;
import ru.mdkardaev.security.dtos.TokenPair;
import ru.mdkardaev.security.dtos.TokenType;
import ru.mdkardaev.security.exceptions.BadCredentialsException;
import ru.mdkardaev.security.jwt.JwtConstants;
import ru.mdkardaev.security.jwt.JwtFactory;
import ru.mdkardaev.security.jwt.JwtValidator;
import ru.mdkardaev.security.requests.SignInRequest;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.TokenRepository;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Component
public class SecurityService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtFactory jwtFactory;
    @Autowired
    private JwtValidator jwtValidator;

    @Transactional
    public TokenPair signIn(SignInRequest request) {
        User user = userRepository.findByLogin(request.getLogin());

        if (user == null
                || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect login or password");
        }

        return issueTokenPair(user);
    }

    @Transactional
    public void signOut(String rawAccessToken) {
        ru.mdkardaev.user.entity.Token accessToken = tokenRepository.findByRawToken(rawAccessToken);
        Long userId = accessToken.getUser().getId();

        List<ru.mdkardaev.user.entity.Token> refreshTokens =
                tokenRepository.findTokenByUser_idAndType(userId, TokenType.REFRESH_TOKEN);

        List<ru.mdkardaev.user.entity.Token> refreshTokensToDelete =
                refreshTokens.stream()
                             .filter(e -> {
                                 String accessTokenId = (String) jwtValidator.validateAndGetClaims(e.getRawToken())
                                                                             .getBody()
                                                                             .get(JwtConstants.CONNECTED_TOKEN);
                                 return accessToken.getId().equals(accessTokenId);
                             })
                             .collect(Collectors.toList());

        tokenRepository.delete(accessToken);
        tokenRepository.delete(refreshTokensToDelete);
    }

    @Transactional
    public TokenPair refresh(String rawRefreshToken) {
        Jws<Claims> refreshClaims = jwtValidator.validateAndGetClaims(rawRefreshToken);
        Claims claims = refreshClaims.getBody();

        String refreshTokenId = claims.getId();
        String accessTokenId = (String) claims.get(JwtConstants.CONNECTED_TOKEN);
        String userLogin = claims.getSubject();
        TokenType tokenType = Optional.ofNullable(claims.get(JwtConstants.TOKEN_TYPE))
                                      .map(Object::toString)
                                      .map(TokenType::valueOf)
                                      .orElse(null);

        User user = userRepository.findByLogin(userLogin);

        if (tokenType != TokenType.REFRESH_TOKEN || user == null) {
            throw new BadCredentialsException("Incorrect token type");
        }

        if (tokenRepository.exists(accessTokenId)) {
            tokenRepository.delete(accessTokenId);
        }
        tokenRepository.delete(refreshTokenId);

        return issueTokenPair(user);
    }

    /**
     * Issue token pair for user
     */
    private TokenPair issueTokenPair(User user) {
        TokenPair tokenPair = jwtFactory.createTokenPair(user);

        Token accessJwt = tokenPair.getAccessToken();
        Token refreshJwt = tokenPair.getRefreshToken();

        ru.mdkardaev.user.entity.Token accessToken =
                ru.mdkardaev.user.entity.Token.builder()
                                              .id(accessJwt.getClaims().getId())
                                              .rawToken(accessJwt.getRawToken())
                                              .type(TokenType.ACCESS_TOKEN)
                                              .expiredTime(accessJwt.getClaims()
                                                                    .getExpiration()
                                                                    .toInstant()
                                                                    .toEpochMilli())
                                              .user(user)
                                              .build();

        ru.mdkardaev.user.entity.Token refreshToken =
                ru.mdkardaev.user.entity.Token.builder()
                                              .id(refreshJwt.getClaims().getId())
                                              .rawToken(refreshJwt.getRawToken())
                                              .type(TokenType.REFRESH_TOKEN)
                                              .expiredTime(refreshJwt.getClaims()
                                                                     .getExpiration()
                                                                     .toInstant()
                                                                     .toEpochMilli())
                                              .user(user)
                                              .build();

        tokenRepository.save(accessToken);
        tokenRepository.save(refreshToken);

        return new TokenPair(accessJwt, refreshJwt);
    }
}
