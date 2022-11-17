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
import ru.job4j.cinema.Main;;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class UserRepositoryTest {

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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void whenAddUser() {
        UserStore store = new UserRepository(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        Optional<User> inDB = store.findUserByEmailAndPhone(user.getEmail(), user.getPhone());
        Assertions.assertThat(inDB.get()).isEqualTo(user);
    }

    @Test
    public void whenNotUniqueEmail() {
        UserStore store = new UserRepository(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        user.setPhone("otherPhone");
        Optional<User> rsl = store.add(user);
        Assertions.assertThat(rsl.isEmpty());
    }

    @Test
    public void whenNotUniquePassword() {
        UserStore store = new UserRepository(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        user.setEmail("otherEmail");
        Optional<User> rsl = store.add(user);
        Assertions.assertThat(rsl.isEmpty());
    }

    @Test
    public void whenNotFindEmail() {
        UserStore store = new UserRepository(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        Optional<User> rsl = store.findUserByEmailAndPhone("1", "phone");
        Assertions.assertThat(rsl.isEmpty());
    }

    @Test
    public void whenNotFindPassword() {
        UserStore store = new UserRepository(new Main().loadPool());
        User user = new User(0, "username", "email", "phone");
        store.add(user);
        Optional<User> rsl = store.findUserByEmailAndPhone("email", "2");
        Assertions.assertThat(rsl.isEmpty());
    }
}