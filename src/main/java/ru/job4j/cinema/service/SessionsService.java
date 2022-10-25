package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.store.SessionsDBStore;

@Service
public class SessionsService {

    private final SessionsDBStore store;

    public SessionsService(SessionsDBStore store) {
        this.store = store;
    }
}
