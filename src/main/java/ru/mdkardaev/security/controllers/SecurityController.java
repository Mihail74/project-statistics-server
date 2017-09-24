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
import ru.mdkardaev.security.jwt.JwtValidator;
import ru.mdkardaev.security.requests.RefreshTokenRequest;
import ru.mdkardaev.security.requests.RegisterUserRequest;
import ru.mdkardaev.security.requests.SignInRequest;
import ru.mdkardaev.security.responses.SignInResponse;
import ru.mdkardaev.security.services.DbUserDetailsService;
import ru.mdkardaev.security.services.SignInOutService;
import ru.mdkardaev.user.services.UserService;

@RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = SwaggerConfig.Tags.SECURITY)
@RestController
public class SecurityController {

    @Autowired
    private DbUserDetailsService userDetailsService;
    @Autowired
    private SignInOutService signInOutService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtValidator jwtValidator;

    @RequestMapping(path = "/register")
    @ApiOperation(value = "Register user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User is successfully registered"),
            @ApiResponse(code = 409, message = "User with the specified login already exist"),
    })
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/signin")
    @ApiOperation(value = "SignIn")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sign in is successful", response = SignInResponse.class),
            @ApiResponse(code = 400, message = "Incorrect login or password")
    })
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        TokenPair tokenPair = signInOutService.signIn(request);

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

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "SignIn")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tokens has been refreshed successful", response = SignInResponse.class),
            @ApiResponse(code = 400, message = "Bad refresh token")
    })
    @RequestMapping(value = "/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {

        TokenPair tokenPair = signInOutService.refresh(request.getRawRefreshToken());

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

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "SignOut")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sign out successful")
    })
    @RequestMapping(value = "api/signout")
    public ResponseEntity<?> signOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = (String) authentication.getCredentials();

        signInOutService.signOut(accessToken);

        return ResponseEntity.ok().build();
    }
}
