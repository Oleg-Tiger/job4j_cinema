package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;
import java.util.List;

public interface SessionStore {
    public Session add(Session session);

    public List<Session> findAll();

    public Session findById(Integer id);

}
