package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public interface TicketStore {

    public Optional<Ticket> addTicket(Ticket ticket);

    public Optional<Ticket> findById(Integer id);
}
