package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService implements AbstractUserService {

    private final UserRepository store;

    public UserService(UserRepository store) {
        this.store = store;
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return store.findUserByEmailAndPhone(email, phone);
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }
}
