package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.AbstractSessionsService;
import ru.job4j.cinema.service.SessionsService;
import ru.job4j.cinema.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class SessionsControllerTest {

    private User user = new User();
    private Model model = mock(Model.class);
    private HttpSession httpSession = mock(HttpSession.class);
    private AbstractSessionsService service = mock(SessionsService.class);
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private SessionsController sessionsController = new SessionsController(service);

    @Test
    public void whenSessions() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "New session", new byte[1]),
                new Session(2, "New session", new byte[1])
        );
        when(service.findAll()).thenReturn(sessions);
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        String page = sessionsController.sessions(model, httpSession);
        verify(model).addAttribute("sessions", sessions);
        verify(model).addAttribute("user", user);
        assertThat(page, is("sessions"));
    }

    @Test
    public void whenAddSession() {
        Session session = new Session();
        String page = sessionsController.addSession(model);
        verify(model).addAttribute("session", session);
        assertThat(page, is("addSession"));
    }

    @Test
    public void whenCreateSession() throws IOException {
        byte[] photo = new byte[1];
        Session session = new Session(0, "Имя", photo);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn(photo);
        String page = sessionsController.createSession(session, file);
        assertThat(page, is("redirect:/sessions"));
    }

    @Test
    public void whenSessionsId() {
        Integer id = 1;
        Session session = new Session(1, "Имя", new byte[1]);
        when(service.findById(id)).thenReturn(session);
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        String page = sessionsController.sessionsId(model, httpSession, id);
        verify(model).addAttribute("ses", session);
        verify(model).addAttribute("user", user);
        verify(httpSession).setAttribute("ses", session);
        assertThat(page, is("hallRow"));
    }

    @Test
    public void whenChoiceRow() {
        Integer row = 1;
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        when(req.getParameter("0")).thenReturn(row.toString());
        String page = sessionsController.choiceRow(httpSession, req);
        verify(httpSession).setAttribute("row", row);
        assertThat(page, is("redirect:/hallSeat"));
    }
}