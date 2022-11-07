package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionsService;
import ru.job4j.cinema.utils.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class SessionsControllerTest {

    @Test
    public void whenSessions() {
        User user = new User();
        List<Session> sessions = Arrays.asList(
                new Session(1, "New session", new byte[1]),
                new Session(2, "New session", new byte[1])
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionsService service = mock(SessionsService.class);
        when(service.findAll()).thenReturn(sessions);
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        SessionsController sessionsController = new SessionsController(service);
        String page = sessionsController.sessions(model, httpSession);
        verify(model).addAttribute("sessions", sessions);
        verify(model).addAttribute("user", user);
        assertThat(page, is("sessions"));
    }

    @Test
    public void whenAddSession() {
        Session session = new Session();
        Model model = mock(Model.class);
        SessionsService service = mock(SessionsService.class);
        SessionsController sessionsController = new SessionsController(service);
        String page = sessionsController.addSession(model);
        verify(model).addAttribute("session", session);
        assertThat(page, is("addSession"));
    }

    @Test
    public void whenCreateSession() throws IOException {
        byte[] photo = new byte[1];
        Session session = new Session(0, "Имя", photo);
        SessionsService service = mock(SessionsService.class);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn(photo);
        SessionsController sessionsController = new SessionsController(service);
        String page = sessionsController.createSession(session, file);
        assertThat(page, is("redirect:/sessions"));
    }

    @Test
    public void whenSessionsId() {
        User user = new User();
        int id = 1;
        Session session = new Session(1, "Имя", new byte[1]);
        SessionsService service = mock(SessionsService.class);
        when(service.findById(id)).thenReturn(session);
        SessionsController sessionsController = new SessionsController(service);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        String page = sessionsController.sessionsId(model, httpSession, id);
        verify(model).addAttribute("ses", session);
        verify(model).addAttribute("user", user);
        assertThat(page, is("hallRow"));
    }

    @Test
    public void whenChoiceRow() {
        User user = new User();
        SessionsService service = mock(SessionsService.class);
        SessionsController sessionsController = new SessionsController(service);
        HttpSession httpSession = mock(HttpSession.class);
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter("0")).thenReturn("1");
        String page = sessionsController.choiceRow(httpSession, req);
        assertThat(page, is("redirect:/hallSeat"));
    }
}