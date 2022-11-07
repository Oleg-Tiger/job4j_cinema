package ru.job4j.cinema.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class TicketDBStoreTest {

    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
    private static Properties property = new Properties();
    private static FileInputStream fis;
    private static String username;
    private static String password;
    private static String url;
    private static String driver;

    static {
        try {
            fis = new FileInputStream("src/test/resources/db.properties");
            property.load(fis);

            username = property.getProperty("jdbc.username");
            password = property.getProperty("jdbc.password");
            url = property.getProperty("jdbc.url");
            driver = property.getProperty("jdbc.driver");
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username(username)
                .password(password)
                .url(url)
                .driverClassName(driver)
                .build();
    }

    @AfterEach
    private void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ticket", "users", "sessions");
    }

    @Test
    public void whenAddTicket() {
        SessionsDBStore sessionStore = new SessionsDBStore(new Main().loadPool());
        Session session = new Session(0, "name", new byte[1]);
        sessionStore.add(session);

        UserDBStore userStore = new UserDBStore(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        userStore.add(user);

        TicketDBStore ticketStore = new TicketDBStore(new Main().loadPool());
        Ticket ticket = new Ticket(0, session.getId(), 1, 1, user.getId());
        ticketStore.addTicket(ticket);
        Optional<Ticket> inDB = ticketStore.findById(ticket.getId());
        Assertions.assertThat(inDB.get()).isEqualTo(ticket);
    }

    @Test
    public void whenNotAddTicket() {
        SessionsDBStore sessionStore = new SessionsDBStore(new Main().loadPool());
        Session session = new Session(0, "name", new byte[1]);
        sessionStore.add(session);

        UserDBStore userStore = new UserDBStore(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        userStore.add(user);

        TicketDBStore ticketStore = new TicketDBStore(new Main().loadPool());
        Ticket ticket = new Ticket(0, session.getId(), 1, 1, user.getId());
        Ticket ticket2 = new Ticket(0, session.getId(), 1, 1, user.getId());
        ticketStore.addTicket(ticket);
        Optional<Ticket> inDB = ticketStore.findById(ticket.getId());
        Optional<Ticket> inDB2 = ticketStore.findById(ticket2.getId());
        Assertions.assertThat(inDB.get()).isEqualTo(ticket);
        Assertions.assertThat(inDB2.isEmpty());
    }

}