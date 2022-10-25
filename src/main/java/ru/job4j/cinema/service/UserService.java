package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.store.UserDBStore;

@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }
}
