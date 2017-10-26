package ru.mdkardaev.user.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.common.config.SwaggerConfig;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.requests.GetUsersRequest;
import ru.mdkardaev.user.responses.GetUsersResponse;
import ru.mdkardaev.user.services.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {SwaggerConfig.Tags.USERS})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of users", response = GetUsersResponse.class)
    public ResponseEntity<?> getUsers(GetUsersRequest request, @AuthenticationPrincipal UserDetails principal) {
        List<UserDTO> users = userService.getUsersExcludeUserWithLogin(principal.getUsername());
        return ResponseEntity.ok(new GetUsersResponse(users));
    }
}
