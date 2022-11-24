package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.User;
import javax.sql.DataSource;
import java.util.Optional;

public class JdbcUserRepositoryTest {

    static DataSource dataSource;
    static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void init() {
        dataSource = new TestDataSourceConfig().dataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterEach
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void whenAddUser() {
        UserRepository store = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        Optional<User> inDB = store.findUserByEmailAndPhone(user.getEmail(), user.getPhone());
        Assertions.assertThat(inDB.get()).isEqualTo(user);
    }

    @Test
    public void whenNotUniqueEmail() {
        UserRepository store = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        user.setPhone("otherPhone");
        Optional<User> rsl = store.add(user);
        Assertions.assertThat(rsl.isEmpty());
    }

    @Test
    public void whenNotUniquePassword() {
        UserRepository store = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        user.setEmail("otherEmail");
        Optional<User> rsl = store.add(user);
        Assertions.assertThat(rsl.isEmpty());
    }

    @Test
    public void whenNotFindEmail() {
        UserRepository store = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        Optional<User> rsl = store.findUserByEmailAndPhone("1", "phone");
        Assertions.assertThat(rsl.isEmpty());
    }

    @Test
    public void whenNotFindPassword() {
        UserRepository store = new JdbcUserRepository(dataSource);
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        Optional<User> rsl = store.findUserByEmailAndPhone("email", "2");
        Assertions.assertThat(rsl.isEmpty());
    }
}