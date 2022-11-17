package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.AbstractSessionsService;
import ru.job4j.cinema.util.UserUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SessionsController {

    private final AbstractSessionsService service;

    public SessionsController(AbstractSessionsService service) {
        this.service = service;
    }

    @GetMapping("/sessions")
    public String sessions(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("sessions", service.findAll());
        return "sessions";
    }

    @GetMapping("/formAddSession")
    public String addSession(Model model) {
        model.addAttribute("session", new Session());
        return "addSession";
    }

    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session session,
                                @RequestParam("file") MultipartFile file) throws IOException {
        session.setPhoto(file.getBytes());
        service.add(session);
        return "redirect:/sessions";
    }

    @GetMapping("/photoSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") Integer sessionId) {
        Session session = service.findById(sessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(session.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(session.getPhoto()));
    }

    @GetMapping("/hallRow/{sessionId}")
    public String sessionsId(Model model, HttpSession httpSession, @PathVariable("sessionId") int id) {
        Session ses = service.findById(id);
        model.addAttribute("ses", ses);
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        httpSession.setAttribute("ses", ses);
        return "hallRow";
    }

    @PostMapping("/choiceRow")
    public String choiceRow(HttpSession httpSession, HttpServletRequest req) {
        Integer row = Integer.valueOf(req.getParameter("0"));
        User user = UserUtil.getUserFromSession(httpSession);
        httpSession.setAttribute("row", row);
        return "redirect:/hallSeat";
    }
}
