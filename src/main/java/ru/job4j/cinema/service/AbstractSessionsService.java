package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;
import java.util.List;
public interface AbstractSessionsService {

    public Session add(Session session);

    public List<Session> findAll();

    public Session findById(Integer id);
}
