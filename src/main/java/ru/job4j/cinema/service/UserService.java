package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return repository.findUserByEmailAndPhone(email, phone);
    }

    public Optional<User> add(User user) {
        return repository.add(user);
    }
}
