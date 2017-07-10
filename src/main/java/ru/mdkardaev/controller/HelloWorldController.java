package ru.mdkardaev.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mihail on 10.07.2017.
 */
@RestController
public class HelloWorldController {

    @RequestMapping(path = "/hello",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String random() {
        return String.valueOf(Math.random());
    }
}
