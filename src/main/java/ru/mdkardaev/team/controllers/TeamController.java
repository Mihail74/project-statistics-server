package ru.mdkardaev.team.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teams")
@Api(tags = {"teams"}, description = "Operations with teams")
public class TeamController {
}
