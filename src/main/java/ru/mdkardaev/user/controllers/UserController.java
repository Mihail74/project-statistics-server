package ru.mdkardaev.user.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
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
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {SwaggerConfig.Tags.USERS})
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of users", response = GetUsersResponse.class)
    public ResponseEntity<?> getUsers(GetUsersRequest request, @AuthenticationPrincipal UserDetails principal) {
        log.debug("getUsers; request is {}", request);

        List<UserDTO> users;

        if (BooleanUtils.isTrue(request.getIncludeMe())) {
            users = userService.getAllUsers();
        } else {
            users = userService.getUsersExcludeUserWithID(Long.valueOf(principal.getUsername()));
        }

        log.debug("getUsers; returns {} matches", users.size());
        return ResponseEntity.ok(new GetUsersResponse(users));
    }
}
