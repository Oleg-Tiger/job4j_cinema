package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import javax.sql.DataSource;
import java.util.Optional;

public class JdbcTicketRepositoryTest {

    static DataSource dataSource;
    static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void init() {
        dataSource = new TestDataSourceConfig().dataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterEach
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tickets", "users", "sessions");
    }

    @Test
    public void whenAddTicket() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = new Session(0, "name", new byte[1]);
        sessionRepository.add(session);

        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        userRepository.add(user);

        TicketRepository ticketRepository = new JdbcTicketRepository(dataSource);
        Ticket ticket = new Ticket(0, session.getId(), 1, 1, user.getId());
        ticketRepository.addTicket(ticket);
        Optional<Ticket> inDB = ticketRepository.findById(ticket.getId());
        Assertions.assertThat(inDB.get()).isEqualTo(ticket);
    }

    @Test
    public void whenNotAddTicket() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = new Session(0, "name", new byte[1]);
        sessionRepository.add(session);

        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        userRepository.add(user);

        TicketRepository ticketRepository = new JdbcTicketRepository(dataSource);
        Ticket ticket = new Ticket(0, session.getId(), 1, 1, user.getId());
        Ticket ticket2 = new Ticket(0, session.getId(), 1, 1, user.getId());
        ticketRepository.addTicket(ticket);
        Optional<Ticket> inDB = ticketRepository.findById(ticket.getId());
        Optional<Ticket> inDB2 = ticketRepository.findById(ticket2.getId());
        Assertions.assertThat(inDB.get()).isEqualTo(ticket);
        Assertions.assertThat(inDB2.isEmpty());
    }

}