package ru.mdkardaev.security.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.security.dtos.TokenPair;
import ru.mdkardaev.security.requests.LoginRequest;
import ru.mdkardaev.security.requests.RefreshTokenRequest;
import ru.mdkardaev.security.requests.RegisterUserRequest;
import ru.mdkardaev.security.responses.LoginResponse;
import ru.mdkardaev.security.responses.RefreshTokenResponse;
import ru.mdkardaev.security.responses.RegisterUserResponse;
import ru.mdkardaev.security.services.AuthorizationService;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.services.UserRegistrationService;
import ru.mdkardaev.user.services.UserService;

import javax.validation.Valid;

@Api(tags = SwaggerConfig.Tags.SECURITY)
@RestController
@RequestMapping(value = "/api/auth",
        method = RequestMethod.POST)
@Slf4j
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegistrationService userRegistrationService;

    @ApiOperation(value = "Register user", response = RegisterUserResponse.class)
    @RequestMapping(path = "/register",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request) {
        log.debug("register; request is {}", request);

        UserDTO user = userRegistrationService.register(request);

        log.debug("register; user with id = {} registered", user.getId());
        return ResponseEntity.ok(new RegisterUserResponse(user));
    }

    @ApiOperation(value = "Login", response = LoginResponse.class)
    @RequestMapping(path = "/login",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.debug("login; user with login = {}", request.getLogin());

        TokenPair tokenPair = authorizationService.login(request);

        LoginResponse response = new LoginResponse(tokenPair.getAccessToken().getRawToken(),
                                                   tokenPair.getRefreshToken().getRawToken(),
                                                   userService.getUserByLogin(request.getLogin()));

        log.debug("login; Login successful");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Logout")
    @RequestMapping(value = "/logout")
    public ResponseEntity<?> logout() {
        log.debug("logout; enter");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = (String) authentication.getCredentials();

        authorizationService.logout(accessToken);

        log.debug("logout; logout successful");
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "Refresh tokens", response = RefreshTokenResponse.class)
    @RequestMapping(value = "/token/refresh",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        log.debug("refreshToken; enter");
        TokenPair tokenPair = authorizationService.refresh(request.getRefreshToken());

        RefreshTokenResponse response = new RefreshTokenResponse(tokenPair.getAccessToken().getRawToken(),
                                                                 tokenPair.getRefreshToken().getRawToken());

        log.debug("refreshToken; refresh successful");
        return ResponseEntity.ok(response);
    }
}
