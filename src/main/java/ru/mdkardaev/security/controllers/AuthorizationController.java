package ru.mdkardaev.security.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import ru.mdkardaev.security.requests.RefreshTokenRequest;
import ru.mdkardaev.security.requests.RegisterUserRequest;
import ru.mdkardaev.security.requests.SignInRequest;
import ru.mdkardaev.security.responses.SignInResponse;
import ru.mdkardaev.security.services.AuthorizationService;
import ru.mdkardaev.user.services.UserService;

import javax.validation.Valid;

@RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = SwaggerConfig.Tags.SECURITY)
@RestController
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Register user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User is successfully registered"),
            @ApiResponse(code = 409, message = "User with the specified login already exist"),
    })
    @RequestMapping(path = "api/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "SignIn")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sign in is successful", response = SignInResponse.class),
            @ApiResponse(code = 400, message = "Incorrect login or password")
    })
    @RequestMapping(path = "api/auth/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        TokenPair tokenPair = authorizationService.signIn(request);

        SignInResponse response = createSignInResponse(tokenPair);

        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "refresh")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tokens has been refreshed successful", response = SignInResponse.class),
            @ApiResponse(code = 400, message = "Bad refresh token")
    })
    @RequestMapping(value = "api/auth/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {

        TokenPair tokenPair = authorizationService.refresh(request.getRawRefreshToken());

        SignInResponse response = createSignInResponse(tokenPair);

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "SignOut")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sign out successful")
    })
    @RequestMapping(value = "api/auth/signout")
    public ResponseEntity<?> signOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = (String) authentication.getCredentials();

        authorizationService.signOut(accessToken);

        return ResponseEntity.ok().build();
    }

    private SignInResponse createSignInResponse(TokenPair tokenPair) {
        SignInResponse response = new SignInResponse();

        response.setAccessToken(tokenPair.getAccessToken().getRawToken());
        response.setAccessTokenExpiredTime(tokenPair.getAccessToken()
                                                   .getClaims()
                                                   .getExpiration()
                                                   .toInstant()
                                                   .toEpochMilli());

        response.setRefreshToken(tokenPair.getRefreshToken().getRawToken());
        response.setRefreshTokenExpiredTime(tokenPair.getRefreshToken()
                                                    .getClaims()
                                                    .getExpiration()
                                                    .toInstant()
                                                    .toEpochMilli());
        return response;
    }
}
