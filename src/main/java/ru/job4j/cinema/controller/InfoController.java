package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class InfoController {

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
}
