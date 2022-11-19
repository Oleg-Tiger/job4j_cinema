package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

class JdbcSessionRepositoryTest {

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
            System.err.println("ОШИБКА: Файл свойств отсутствует!");
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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "sessions");
    }

    @Test
    public void whenAddSession() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource());
        Session session = new Session(0, "name", new byte[1]);
        sessionRepository.add(session);
        Session inDB = sessionRepository.findById(session.getId());
        Assertions.assertThat(inDB.getName()).isEqualTo(session.getName());
    }

    @Test
    public void whenFindAll() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource());
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