package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("/hallSeat")
    public String hallSeat(HttpSession httpSession, Model model) {
        model.addAttribute("ses", httpSession.getAttribute("ses"));
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
        return "info";
    }

    @GetMapping("/result")
    public String result(HttpSession httpSession, Model model) {
        Session session = (Session) httpSession.getAttribute("ses");
        Integer row = (Integer) httpSession.getAttribute("row");
        Integer seat = (Integer) httpSession.getAttribute("seat");
        Ticket ticket = new Ticket(0, session.getId(), row, seat, 0);
        boolean result = service.addTicket(ticket);
        model.addAttribute("row", row);
        model.addAttribute("seat", seat);
        if (result) {
            return "success";
        }
        return "occupied";
    }

    @GetMapping("occupied")
    public String occupied() {
        return "occupied";
    }

    @GetMapping("success")
    public String success(HttpSession httpSession, Model model) {
        model.addAttribute("ses", httpSession.getAttribute("ses"));
        model.addAttribute("row", httpSession.getAttribute("row"));
        model.addAttribute("seat", httpSession.getAttribute("seat"));
        return "success";
    }
}
