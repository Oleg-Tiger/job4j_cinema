package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;
import java.util.Optional;

public interface AbstractUserService {

    public Optional<User> findUserByEmailAndPhone(String email, String phone);

    public Optional<User> add(User user);

}
