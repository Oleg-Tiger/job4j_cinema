package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.service.TicketService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/choiceRow")
    public String choiceRow(HttpSession httpSession, HttpServletRequest req) {
       Integer row = Integer.valueOf(req.getParameter("0"));
       httpSession.setAttribute("row", row);
       return "redirect:/hallSeat";
    }

    @GetMapping("/hallSeat")
    public String hallSeat(HttpSession httpSession, Model model) {
        model.addAttribute("ses", httpSession.getAttribute("ses"));
        return "hallSeat";
    }
}
