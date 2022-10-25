package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import ru.job4j.cinema.service.TicketService;

@Controller
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }
}
