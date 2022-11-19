package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserRepository {
    public Optional<User> add(User user);

    public Optional<User> findUserByEmailAndPhone(String email, String phone);
}
