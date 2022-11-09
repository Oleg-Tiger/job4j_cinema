package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;

@Service
public class SessionsService {

    private final SessionRepository repository;

    public SessionsService(SessionRepository repository) {
        this.repository = repository;
    }

    public Session add(Session session) {
        return repository.add(session);
    }

    public List<Session> findAll() {
        return repository.findAll();
    }

    public Session findById(Integer id) {
        return repository.findById(id);
    }
}
