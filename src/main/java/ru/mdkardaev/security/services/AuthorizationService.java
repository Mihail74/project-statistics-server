package ru.mdkardaev.security.services;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mdkardaev.security.dtos.Token;
import ru.mdkardaev.security.dtos.TokenPair;
import ru.mdkardaev.security.dtos.TokenType;
import ru.mdkardaev.security.jwt.JwtConstants;
import ru.mdkardaev.security.jwt.JwtFactory;
import ru.mdkardaev.security.jwt.JwtValidator;
import ru.mdkardaev.security.requests.LoginRequest;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.TokenRepository;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class AuthorizationService {

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
    public TokenPair login(LoginRequest request) {
        User user = userRepository.findByLogin(request.getLogin());

        if (user == null
                || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect login or password");
        }

        return issueTokenPair(user);
    }

    @Transactional
    public void logout(String rawAccessToken) {
        ru.mdkardaev.user.entity.Token accessToken = tokenRepository.findByRawToken(rawAccessToken);
        Long userId = accessToken.getUser().getId();

        List<ru.mdkardaev.user.entity.Token> refreshTokens =
                tokenRepository.findTokenByUser_idAndType(userId, TokenType.REFRESH_TOKEN);

        List<ru.mdkardaev.user.entity.Token> refreshTokensToDelete =
                refreshTokens.stream()
                        .filter(e -> {
                            String accessTokenId = (String) jwtValidator.getClaimsIncludeExpired(e.getRawToken())
                                    .get(JwtConstants.CONNECTED_TOKEN);
                            return accessToken.getId().equals(accessTokenId);
                        })
                        .collect(Collectors.toList());

        tokenRepository.delete(accessToken);
        tokenRepository.delete(refreshTokensToDelete);
    }

    @Transactional
    public TokenPair refresh(String rawRefreshToken) {
        Claims claims = jwtValidator.validateAndGetClaims(rawRefreshToken);

        String refreshTokenId = claims.getId();
        String accessTokenId = (String) claims.get(JwtConstants.CONNECTED_TOKEN);
        Long userID = Long.valueOf(claims.getSubject());
        TokenType tokenType = TokenType.valueOf(claims.get(JwtConstants.TOKEN_TYPE).toString());

        User user = userRepository.findOne(userID);

        ru.mdkardaev.user.entity.Token refreshToken = tokenRepository.findOne(refreshTokenId);

        if (refreshToken == null || tokenType != TokenType.REFRESH_TOKEN) {
            throw new BadCredentialsException("Incorrect token");
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
