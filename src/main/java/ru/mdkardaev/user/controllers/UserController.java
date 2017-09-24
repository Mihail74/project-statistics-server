package ru.mdkardaev.user.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.requests.GetUsersRequest;
import ru.mdkardaev.user.responses.GetUsersResponse;
import ru.mdkardaev.user.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users")
@Api(tags = {"users"}, description = "Operations with users")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(path = "/",
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
