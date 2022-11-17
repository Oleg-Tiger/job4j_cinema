package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;
import java.util.Optional;

public interface AbstractTicketService {

    public Optional<Ticket> addTicket(Ticket ticket);
}
