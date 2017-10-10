package ru.mdkardaev.security.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
@RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegistrationService userRegistrationService;

    @ApiOperation(value = "Register user")
    @ApiResponse(code = 200, message = "User is registered successfully", response = RegisterUserResponse.class)
    @RequestMapping(path = "api/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request) {
        UserDTO user = userRegistrationService.register(request);
        return ResponseEntity.ok(new RegisterUserResponse(user));
    }

    @ApiOperation(value = "Login")
    @ApiResponse(code = 200, message = "Login is successful", response = LoginResponse.class)
    @RequestMapping(path = "api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        TokenPair tokenPair = authorizationService.login(request);
        LoginResponse response = new LoginResponse(tokenPair.getAccessToken().getRawToken(),
                                                   tokenPair.getRefreshToken().getRawToken(),
                                                   userService.getUserByLogin(request.getLogin()));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Logout")
    @ApiResponse(code = 200, message = "Logout is successful")
    @RequestMapping(value = "api/auth/logout")
    public ResponseEntity<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = (String) authentication.getCredentials();

        authorizationService.logout(accessToken);

        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "refresh")
    @ApiResponse(code = 200, message = "Tokens have been refreshed successful", response = RefreshTokenResponse.class)
    @RequestMapping(value = "api/auth/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authorizationService.refresh(request.getRawRefreshToken());

        RefreshTokenResponse response = new RefreshTokenResponse(tokenPair.getAccessToken().getRawToken(),
                                                                 tokenPair.getRefreshToken().getRawToken());
        return ResponseEntity.ok(response);
    }
}
