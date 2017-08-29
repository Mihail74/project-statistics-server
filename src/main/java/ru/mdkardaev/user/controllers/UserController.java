package ru.mdkardaev.user.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.requests.GetUsersRequest;
import ru.mdkardaev.user.requests.RegisterUserRequest;
import ru.mdkardaev.user.responses.GetUsersResponse;
import ru.mdkardaev.user.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
@Api(tags = {"users"}, description = "Operations with users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is successfully  registered"),
            @ApiResponse(code = 409, message = "User with the specified email already exist")
    })
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return users list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return users list", response = GetUsersResponse.class),
    })
    public ResponseEntity<?> getUsers(GetUsersRequest request) {
        List<UserDTO> users = userService.getUsers();
        return ResponseEntity.ok(new GetUsersResponse(users));
    }
}
