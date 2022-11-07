package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.utils.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserService userService = mock(UserService.class);
    private UserController userController = new UserController(userService);
    private Model model = mock(Model.class);
    private  User user = new User(1, "name", "email", "phone");

    @Test
    public void whenRegistrationFail() {
        when(userService.add(user)).thenReturn(Optional.empty());
        String page = userController.registration(model, user);
        verify(model).addAttribute("message", "Данная почта или номер телефона уже используются");
        assertThat(page, is("redirect:/registrationPage?fail=true"));
    }

    @Test
    public void whenRegistrationSuccess() {
        when(userService.add(user)).thenReturn(Optional.of(user));
        String page = userController.registration(model, user);
        assertThat(page, is("redirect:/loginPage"));
    }

    @Test
    public void whenRegistrationPage() {
        boolean fail = true;
        HttpSession httpSession = mock(HttpSession.class);
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        String page = userController.registrationPage(model, httpSession, fail);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("fail", fail);
        assertThat(page, is("registration"));
    }

    @Test
    public void loginPage() {
        boolean fail = true;
        String page = userController.loginPage(model, fail);
        verify(model).addAttribute("fail", fail);
        assertThat(page, is("login"));
    }

    @Test
    public void whenLoginSuccess() {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequestWrapper.class);
        when(req.getSession()).thenReturn(httpSession);
        when(userService.findUserByEmailAndPhone("email", "phone")).thenReturn(Optional.of(user));
        String page = userController.login(user, req);
        verify(httpSession).setAttribute("user", user);
        assertThat(page, is("redirect:/sessions"));
    }

    @Test
    public void whenLoginFail() {
        HttpServletRequest req = mock(HttpServletRequestWrapper.class);
        when(userService.findUserByEmailAndPhone("email", "phone")).thenReturn(Optional.empty());
        String page = userController.login(user, req);
        assertThat(page, is("redirect:/loginPage?fail=true"));
    }

    @Test
    public void whenLogout() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        HttpSession httpSession = mock(HttpSession.class);
        String page = userController.logout(httpSession);
        assertThat(page, is("redirect:/loginPage"));
    }
}