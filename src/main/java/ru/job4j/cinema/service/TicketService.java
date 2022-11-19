package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

@Service
public class TicketService implements AbstractTicketService {

    private final TicketRepository store;

    public TicketService(TicketRepository store) {
        this.store = store;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return store.addTicket(ticket);
    }
}
