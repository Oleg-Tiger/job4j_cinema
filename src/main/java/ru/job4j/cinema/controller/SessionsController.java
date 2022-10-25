package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import ru.job4j.cinema.service.SessionsService;

@Controller
public class SessionsController {

    private final SessionsService service;

    public SessionsController(SessionsService service) {
        this.service = service;
    }
}
