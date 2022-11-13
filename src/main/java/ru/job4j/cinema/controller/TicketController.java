package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("/hallSeat")
    public String hallSeat(HttpSession httpSession, Model model) {
        model.addAttribute("ses", httpSession.getAttribute("ses"));
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        return "hallSeat";
    }

    @PostMapping("/info")
    public String info(HttpSession httpSession, HttpServletRequest req) {
        Integer seat = Integer.valueOf(req.getParameter("0"));
        httpSession.setAttribute("seat", seat);
        return "redirect:/info";
    }

    @GetMapping("/info")
    public String info(HttpSession httpSession, Model model) {
        model.addAttribute("ses", httpSession.getAttribute("ses"));
        model.addAttribute("row", httpSession.getAttribute("row"));
        model.addAttribute("seat", httpSession.getAttribute("seat"));
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        return "info";
    }

    @GetMapping("/result")
    public String result(HttpSession httpSession, Model model) {
        Session session = (Session) httpSession.getAttribute("ses");
        Integer row = (Integer) httpSession.getAttribute("row");
        Integer seat = (Integer) httpSession.getAttribute("seat");
        User user = (User) httpSession.getAttribute("user");
        Ticket ticket = new Ticket(0, session.getId(), row, seat, user.getId());
        Optional<Ticket> result = service.addTicket(ticket);
        model.addAttribute("row", row);
        model.addAttribute("seat", seat);
        model.addAttribute("ses", session);
        model.addAttribute("user", user);
        if (result.isEmpty()) {
            return "occupied";
        }
        return "success";
    }

    @GetMapping("occupied")
    public String occupied() {
        return "occupied";
    }

    @GetMapping("success")
    public String success() {
        return "success";
    }
}
