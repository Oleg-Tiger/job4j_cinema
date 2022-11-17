package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.AbstractTicketService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    private Session session = new Session(0, "Имя", new byte[1]);
    private Integer row = 1;
    private Integer seat = 2;
    private User user = new User();
    private AbstractTicketService ticketService = mock(TicketService.class);
    private HttpSession httpSession = mock(HttpSession.class);
    private Model model = mock(Model.class);
    private TicketController ticketController = new TicketController(ticketService);

    @Test
    public void whenHallSeat() {
        when(httpSession.getAttribute("ses")).thenReturn(session);
        String page = ticketController.hallSeat(httpSession, model);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("ses", session);
        assertThat(page, is("hallSeat"));
    }

    @Test
    public void whenInfoPost() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter("0")).thenReturn(seat.toString());
        String page = ticketController.info(httpSession, req);
        verify(httpSession).setAttribute("seat", seat);
        assertThat(page, is("redirect:/info"));
    }

    @Test
    public void whenInfoGet() {
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        when(httpSession.getAttribute("ses")).thenReturn(session);
        when(httpSession.getAttribute("row")).thenReturn(row);
        when(httpSession.getAttribute("seat")).thenReturn(seat);
        Model model = mock(Model.class);
        String page = ticketController.info(httpSession, model);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("ses", session);
        verify(model).addAttribute("row", row);
        verify(model).addAttribute("seat", seat);
        assertThat(page, is("info"));
    }

    @Test
    public void whenResultSuccess() {
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        when(httpSession.getAttribute("ses")).thenReturn(session);
        when(httpSession.getAttribute("row")).thenReturn(row);
        when(httpSession.getAttribute("seat")).thenReturn(seat);
        Ticket ticket = new Ticket(0, session.getId(), row, seat, user.getId());
        when(ticketService.addTicket(ticket)).thenReturn(Optional.of(ticket));
        String page = ticketController.result(httpSession, model);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("ses", session);
        verify(model).addAttribute("row", row);
        verify(model).addAttribute("seat", seat);
        assertThat(page, is("success"));
    }

    @Test
    public void whenResultOccupied() {
        when(UserUtil.getUserFromSession(httpSession)).thenReturn(user);
        when(httpSession.getAttribute("ses")).thenReturn(session);
        when(httpSession.getAttribute("row")).thenReturn(row);
        when(httpSession.getAttribute("seat")).thenReturn(seat);
        Ticket ticket = new Ticket(0, session.getId(), row, seat, user.getId());
        when(ticketService.addTicket(ticket)).thenReturn(Optional.empty());
        String page = ticketController.result(httpSession, model);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("ses", session);
        verify(model).addAttribute("row", row);
        verify(model).addAttribute("seat", seat);
        assertThat(page, is("occupied"));
    }
}
