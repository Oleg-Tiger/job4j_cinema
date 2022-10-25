package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.store.TicketDBStore;

@Service
public class TicketService {

    private final TicketDBStore store;

    public TicketService(TicketDBStore store) {
        this.store = store;
    }
}
