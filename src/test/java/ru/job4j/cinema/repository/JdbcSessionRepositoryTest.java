package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.Session;
import javax.sql.DataSource;
import java.util.List;

public class JdbcSessionRepositoryTest {

    static DataSource dataSource;
    static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void init() {
        dataSource = new TestDataSourceConfig().dataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterEach
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "sessions");
    }

    @Test
    public void whenAddSession() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = new Session(0, "name", new byte[1]);
        sessionRepository.add(session);
        Session inDB = sessionRepository.findById(session.getId());
        Assertions.assertThat(inDB.getName()).isEqualTo(session.getName());
    }

    @Test
    public void whenFindAll() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = new Session(0, "name", new byte[1]);
        Session session2 = new Session(0, "name2", new byte[1]);
        List<Session> resultBeforeAdd = sessionRepository.findAll();
        sessionRepository.add(session);
        sessionRepository.add(session2);
        List<Session> result = sessionRepository.findAll();
        Assertions.assertThat(resultBeforeAdd.size() + 2).isEqualTo(result.size());
        Assertions.assertThat(result).contains(session, session2);
    }
}