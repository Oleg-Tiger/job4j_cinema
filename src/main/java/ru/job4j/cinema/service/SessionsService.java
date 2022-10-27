package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.store.SessionsDBStore;

import java.util.List;

@Service
public class SessionsService {

    private final SessionsDBStore store;

    public SessionsService(SessionsDBStore store) {
        this.store = store;
    }

    public Session add(Session session) {
        return store.add(session);
    }

    public List<Session> findAll() {
        return store.findAll();
    }

    public Session findById(Integer id) {
        return store.findById(id);
    }
}
