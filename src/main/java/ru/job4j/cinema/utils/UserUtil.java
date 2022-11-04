package ru.job4j.cinema.utils;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public final class UserUtil {

    private UserUtil() {
    }

    public static User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        return user;
    }
}